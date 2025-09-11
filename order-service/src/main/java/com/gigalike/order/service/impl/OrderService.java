package com.gigalike.order.service.impl;

import com.gigalike.order.dto.data.CouponResponse;
import com.gigalike.order.dto.data.ProductDto;
import com.gigalike.order.dto.data.ProductDurationDto;
import com.gigalike.order.dto.data.UpdateUserAmount;
import com.gigalike.order.dto.request.CreateOrderDto;
import com.gigalike.order.entity.*;
import com.gigalike.order.feign.AuthClient;
import com.gigalike.order.feign.MarketingClient;
import com.gigalike.order.feign.ProductClient;
import com.gigalike.order.repository.CartItemRepository;
import com.gigalike.order.repository.CartRepository;
import com.gigalike.order.repository.OrderItemRepository;
import com.gigalike.order.repository.OrderRepository;
import com.gigalike.order.service.IOrderService;
import com.gigalike.shared.constant.CouponType;
import com.gigalike.shared.constant.OrderStatus;
import com.gigalike.shared.dto.ApiResponse;
import com.gigalike.shared.exception.BusinessException;
import com.gigalike.shared.exception.NotFoundException;
import com.gigalike.shared.util.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService implements IOrderService {
    CartRepository cartRepository;
    OrderRepository orderRepository;
    ProductClient productClient;
    OrderItemRepository orderItemRepository;
    CartItemRepository cartItemRepository;
    AuthClient authClient;
    MarketingClient marketingClient;

    @Override
    @Transactional
    public void createOrderFromCart(CreateOrderDto createOrderDto) {
        Cart cart = cartRepository.findFirstById(createOrderDto.getCartId())
                .orElseThrow(() -> new NotFoundException("Cart with id " + createOrderDto.getCartId() + "not found"));
        BigDecimal totalAmount = getTotalAmount(cart.getTotalAmount(), createOrderDto.getCouponCode());

        verifyCartContent(cart);
        String userName = SecurityUtil.getCurrentUsername();
        Order order = null;

        try {
            // Step 1: tạo order
            order = createOrderFromCart(cart, totalAmount);

            // Step 2: trừ tiền
            deductUserBalance(userName, totalAmount);
        } catch (Exception e) {
            // rollback nếu bước 2 hoặc 3 lỗi
            if (order != null) {
                rollbackOrder(order.getId());       // undo tạo order
            }
            // nếu trừ tiền thất bại nhưng đã trừ tiền -> refund
            refundUserBalance(userName, totalAmount);
            throw new BusinessException("Checkout failed: " + e.getMessage(), e);
        }
    }

    public void deductUserBalance(String userName, BigDecimal amount) {
        UpdateUserAmount update = UpdateUserAmount.builder()
                .amount(amount.negate()) // trừ tiền
                .build();
        authClient.updateUserAmount(userName, update);
        log.info("Deducted user balance for user {}: {}", userName, amount);
    }

    // Compensation: cộng tiền lại
    public void refundUserBalance(String userName, BigDecimal amount) {
        UpdateUserAmount update = UpdateUserAmount.builder()
                .amount(amount) // cộng lại
                .build();
        authClient.updateUserAmount(userName, update);
        log.info("Refund User balance for user {} : {}", userName, amount);
    }

    @Transactional
    public Order createOrderFromCart(Cart cart, BigDecimal totalAmount) {
        var order = Order.builder()
                .status(OrderStatus.PENDING)
                .totalAmount(totalAmount)
                .userId(cart.getUserId())
                .build();
        orderRepository.save(order);
        createOrderDetail(order, cart);
        return order;
    }


    @Transactional
    public void rollbackOrder(UUID orderId) {
        orderItemRepository.deleteByOrderId(orderId);
        orderRepository.deleteById(orderId);
    }

    private void createOrderDetail(Order order, Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findAllByCart_Id(cart.getId());
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productDuration(cartItem.getProductDurationId())
                    .productId(cartItem.getProductId())
                    .quantity(cartItem.getQuantity())
                    .orderInfo("Vui lòng đợi trong giây lát")
                    .build();
            orderItemRepository.save(orderItem);
            log.info("Save order detail success");
        }

        log.info("Save all order detail success");
    }

    private void verifyCartContent(Cart cart) {
        BigDecimal verifyPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getItems()) {
            ApiResponse<ProductDto> productDtoResponse = productClient.getProductDetail(cartItem.getProductId());
            ProductDurationDto productDurationDto = productDtoResponse.getData()
                    .getProductDurations()
                    .stream()
                    .filter((t) -> t.getProductDurationId() == cartItem.getProductDurationId())
                    .findFirst().get();

            BigDecimal cartItemPrice = productDurationDto.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            verifyPrice = verifyPrice.add(cartItemPrice);

        }
        if (verifyPrice.compareTo(cart.getTotalAmount()) != 0) {
            throw new BusinessException("Invalid order price! Please try again");
        }
    }

    private BigDecimal getTotalAmount(BigDecimal currentAmount, String couponCode) {
        if (couponCode == null || couponCode.isEmpty()) {
            return currentAmount;
        }
        ApiResponse<CouponResponse> couponResponse = marketingClient.getCouponDetailByCouponCode(couponCode);
        if (couponResponse == null || couponResponse.getData() == null) {
            throw new BusinessException("Coupon " + couponCode + " not found");
        }

        if(couponResponse.getData().getTotalUsed() == couponResponse.getData().getMaxUsed()) {
            log.info("Coupon {} max used exceeded", couponCode);
            throw new BusinessException("Coupon " + couponCode + " max used exceeded");
        }

        if(couponResponse.getData().getExpiredIn().isBefore(LocalDateTime.now())) {
            log.info("Coupon {} expired", couponCode);
            throw new BusinessException("Coupon " + couponCode + " is expired!");
        }

        BigDecimal value = couponResponse.getData().getValue();

        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Coupon " + couponCode + " is invalid");
        }

        if (couponResponse.getData().getType() == CouponType.FIXED_AMOUNT) {
            var newAmount = currentAmount.subtract(value);
            log.info("Fixed amount for {}: {}", couponCode, newAmount);
        }

        if (couponResponse.getData().getType() == CouponType.PERCENTAGE) {
            BigDecimal discount = currentAmount.multiply(value)
                    .divide(BigDecimal.valueOf(100), 0, RoundingMode.DOWN);
            return currentAmount.subtract(discount);
        }
        throw new BusinessException("Coupon " + couponCode + " is invalid");
    }

}
