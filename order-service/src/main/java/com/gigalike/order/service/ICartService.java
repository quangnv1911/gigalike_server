package com.gigalike.order.service;

import com.gigalike.order.dto.request.AddToCartRqDto;
import com.gigalike.order.dto.request.UpdateCartItemQuantity;
import com.gigalike.order.dto.response.GetCartResponse;

import java.util.UUID;

public interface ICartService {
    String addProductToCart(AddToCartRqDto addToCartRqDto);
    String removeProductFromCart(UUID cartItemId);

    String updateCartItemQuantity(UpdateCartItemQuantity updateCartItemQuantity);

    GetCartResponse getCurrentCart(String accessToken);
}
