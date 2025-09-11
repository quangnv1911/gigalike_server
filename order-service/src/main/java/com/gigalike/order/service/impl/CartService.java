package com.gigalike.order.service.impl;

import com.gigalike.order.dto.data.ProductDto;
import com.gigalike.order.dto.request.AddToCartRqDto;
import com.gigalike.order.dto.request.UpdateCartItemQuantity;
import com.gigalike.order.dto.response.CartItemDto;
import com.gigalike.order.dto.response.GetCartResponse;
import com.gigalike.order.entity.Cart;
import com.gigalike.order.entity.CartItem;
import com.gigalike.order.feign.ProductClient;
import com.gigalike.order.repository.CartItemRepository;
import com.gigalike.order.repository.CartRepository;
import com.gigalike.order.service.ICartService;
import com.gigalike.shared.dto.ApiResponse;
import com.gigalike.shared.exception.BusinessException;
import com.gigalike.shared.exception.NotFoundException;
import com.gigalike.shared.util.JwtUtil;
import com.gigalike.shared.util.SecurityUtil;
import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService implements ICartService {
    CartItemRepository cartItemRepository;
    CartRepository cartRepository;
    JwtUtil jwtUtil;
    ProductClient productClient;

    @Override
    @Transactional
    public String addProductToCart(AddToCartRqDto addToCartRqDto) {
        UUID userId = jwtUtil.extractUserId(addToCartRqDto.getAccessToken());

        var cart = findOrCreateCart(addToCartRqDto, userId);
        mapToCartItem(cart, addToCartRqDto);

        ApiResponse<ProductDto> productDtoResponse = productClient.getProductDetail(addToCartRqDto.getProductId());

        // Tinh tien
        BigDecimal totalAmount = Optional.ofNullable(productDtoResponse.getData()).map(ProductDto::getProductDurations).orElse(Collections.emptyList()).stream().filter(d -> d.getProductDurationId().equals(addToCartRqDto.getProductDurationId())).findFirst().map(d -> d.getPrice().multiply(BigDecimal.valueOf(addToCartRqDto.getQuantity()))).orElseThrow(() -> new NotFoundException(String.format("ProductDuration %s not found in product %s", addToCartRqDto.getProductDurationId(), addToCartRqDto.getProductId())));

        cart.setTotalAmount(totalAmount);
        return "Add product to cart successfully!";
    }

    @Override
    @Transactional
    public String removeProductFromCart(UUID cartItemId) {
        // Lấy CartItem và Cart
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Cart Item with id %s not found!", cartItemId)
                ));

        Cart cart = Optional.ofNullable(cartItem.getCart())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Cart for cartItem %s not found!", cartItemId)
                ));

        // Lấy product info
        ApiResponse<ProductDto> productDtoResponse = productClient.getProductDetail(cartItem.getProductId());
        ProductDto productDto = productDtoResponse.getData();
        if (productDto == null) {
            throw new NotFoundException(String.format("Product %s not found!", cartItem.getProductId()));
        }

        // Tính tiền
        BigDecimal totalSubPrice = productDto.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        BigDecimal currentTotal = cart.getTotalAmount();

        if (currentTotal.compareTo(totalSubPrice) < 0) {
            log.warn("Cart validation failed: cartId={}, productId={}, currentTotal={}, expectedMinTotal={}",
                    cart.getId(),
                    productDto.getId(),
                    currentTotal,
                    totalSubPrice);

            throw new BusinessException(String.format(
                    "Total amount %s is smaller than expected minimum %s!",
                    currentTotal,
                    totalSubPrice
            ));
        }

        // Xóa cart item
        cartItemRepository.delete(cartItem);
        log.info("Removed cart item with id={} (productId={}) from cartId={} successfully",
                cartItem.getId(),
                cartItem.getProductId(),
                cart.getId());

        return "Remove product from cart successfully!";
    }


    @Override
    @Transactional
    public String updateCartItemQuantity(UpdateCartItemQuantity updateCartItemQuantity) {
        var cartItem = cartItemRepository.findById(updateCartItemQuantity.getCartItemId()).orElseThrow(() -> new NotFoundException(String.format("Cart Item with id %s not found!", updateCartItemQuantity.getCartItemId())));
        if (updateCartItemQuantity.getQuantity() <= 0) {
            cartItemRepository.delete(cartItem);
            return "Remove product from cart successfully!";
        }
        cartItem.setQuantity(updateCartItemQuantity.getQuantity());
        cartItemRepository.save(cartItem);


        // Cap nhat gia tien
        var cart = cartRepository.findFirstById(updateCartItemQuantity.getCartId());
        if (cart.isEmpty()) {
            throw new NotFoundException("Cart with id " + updateCartItemQuantity.getCartId() + " not found!");
        }

        ApiResponse<ProductDto> productDtoResponse = productClient.getProductDetail(cartItem.getProductId());

        // Tinh tien
        BigDecimal currentProductPrice = Optional.ofNullable(productDtoResponse.getData()).map(ProductDto::getProductDurations).orElse(Collections.emptyList()).stream().filter(d -> d.getProductDurationId().equals(cartItem.getProductDurationId())).findFirst().orElseThrow(() -> new NotFoundException(String.format("ProductDuration %s not found in product %s", cartItem.getProductDurationId(), cartItem.getProductId()))).getPrice();

        // Chênh lệch quantity
        int quantityDiff = updateCartItemQuantity.getQuantity() - cartItem.getQuantity();

        // Tính tổng amount: nếu quantityDiff âm, sẽ trừ, nếu dương sẽ cộng
        BigDecimal updateAmount = currentProductPrice.multiply(BigDecimal.valueOf(quantityDiff));

        // Cập nhật totalAmount
        BigDecimal newTotalAmount = cart.get().getTotalAmount().add(updateAmount);
        if (newTotalAmount.compareTo(BigDecimal.ZERO) < 0) {
            newTotalAmount = BigDecimal.ZERO; // tránh âm
        }

        cart.get().setTotalAmount(newTotalAmount);
        return "Update quantity successfully!";
    }


    @Override
    public GetCartResponse getCurrentCart(String accessToken) {
        UUID userId = jwtUtil.extractUserId(accessToken);
        var cart = cartRepository.findFirstByUserId(userId);
        if (cart.isEmpty()) {
            return null;
        }

        var cartItems = cartItemRepository.findAllByCart_Id(cart.get().getId());

        List<CartItemDto> lisCartItemsDto = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            var productDetail = productClient.getProductDetail(cartItem.getProductId());
            var newCartItemDto = CartItemDto.builder().cartItemId(cartItem.getId()).price(productDetail.getData().getPrice()).productName(productDetail.getData().getName()).imageUrls(productDetail.getData().getImageUrls()).productId(productDetail.getData().getId()).productId(productDetail.getData().getId()).quantity(cartItem.getQuantity()).build();
            lisCartItemsDto.add(newCartItemDto);
        }

        return GetCartResponse.builder().cartItems(lisCartItemsDto).cartId(cart.get().getId()).totalPrice(cart.get().getTotalAmount()).build();

    }

    private Cart findOrCreateCart(AddToCartRqDto addToCartRqDto, UUID userId) {
        var cart = cartRepository.findFirstByUserId(userId);
        if (cart.isPresent()) {
            return cart.get();
        }
        log.info("Do not find any cart of user {}", userId);
        var newCart = Cart.builder().userId(userId).build();
        return cartRepository.save(newCart);
    }

    private void mapToCartItem(Cart cart, AddToCartRqDto addToCartRqDto) {
        var newCartItem = CartItem.builder().cart(cart).productId(addToCartRqDto.getProductId()).quantity(addToCartRqDto.getQuantity()).productDurationId(addToCartRqDto.getProductDurationId()).build();
        cartItemRepository.save(newCartItem);
        log.info("Cart item has been saved {}", newCartItem);
    }
}
