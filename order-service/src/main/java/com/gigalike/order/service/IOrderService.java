package com.gigalike.order.service;

import com.gigalike.order.dto.request.CreateOrderDto;

public interface IOrderService {
    void createOrderFromCart(CreateOrderDto createOrderDto);
}
