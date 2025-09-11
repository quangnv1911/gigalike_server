package com.gigalike.payment.controller;

import com.gigalike.payment.dto.request.GenerateNewPaymentRequest;
import com.gigalike.payment.dto.response.GenerateNewPaymentResponse;
import com.gigalike.payment.service.IPaymentService;
import com.gigalike.shared.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    IPaymentService paymentService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<?>> generateNewPayment(@Valid @RequestBody GenerateNewPaymentRequest request) {
        GenerateNewPaymentResponse response = paymentService.generateNewPayment(request);
        return ResponseEntity.ok(ApiResponse.success("Create new payment successfully", response));
    }
}
