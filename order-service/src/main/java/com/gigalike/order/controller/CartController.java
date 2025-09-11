package com.gigalike.order.controller;

import com.gigalike.order.dto.request.AddToCartRqDto;
import com.gigalike.order.dto.request.UpdateCartItemQuantity;
import com.gigalike.order.service.ICartService;
import com.gigalike.shared.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    ICartService cartService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<?>> addProductToCart(@RequestBody AddToCartRqDto addToCartRqDto) {
        var response = cartService.addProductToCart(addToCartRqDto);
        return ResponseEntity.ok(ApiResponse.success("Add to product successfully", response));
    }

    @PostMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse<?>> removeProductFromCart(@PathVariable UUID cartItemId) {
        var response = cartService.removeProductFromCart(cartItemId);
        return ResponseEntity.ok(ApiResponse.success("Add to product successfully", response));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<?>> updateCartItemQuantity(@RequestBody UpdateCartItemQuantity updateCartItemQuantity) {
        var response = cartService.updateCartItemQuantity(updateCartItemQuantity);
        return ResponseEntity.ok(ApiResponse.success("Add to product successfully", response));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<?>> getCurrentCart(@RequestHeader("Authorization") String accessToken) {
        String token = accessToken.startsWith("Bearer ")
                ? accessToken.substring(7)
                : accessToken;
        var response = cartService.getCurrentCart(token);
        return ResponseEntity.ok(ApiResponse.success("Add to product successfully", response));
    }

}
