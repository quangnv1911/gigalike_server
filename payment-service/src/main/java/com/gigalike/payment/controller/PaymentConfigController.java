package com.gigalike.payment.controller;

import com.gigalike.payment.dto.data.PaymentConfigDto;
import com.gigalike.payment.service.IPaymentConfigService;
import com.gigalike.shared.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment-config")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentConfigController {
    IPaymentConfigService paymentConfigService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<PaymentConfigDto>>> getAllPaymentConfig() {
        var response = paymentConfigService.getAllPaymentConfig();
        return ResponseEntity.ok(ApiResponse.success("Get all payment method successfully", response));
    }

    @GetMapping("/bank-info")
    public ResponseEntity<ApiResponse<?>> getAllBankInfo() {
        var response = paymentConfigService.getAllBankInfo();
        return ResponseEntity.ok(ApiResponse.success("Get all bank info successfully", response));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<?>> createPaymentConfig(@Valid @RequestBody PaymentConfigDto paymentDto) {
        var response = paymentConfigService.createPaymentConfig(paymentDto);
        return ResponseEntity.ok(ApiResponse.success("Get all payment method successfully", response));
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<?>> updatePaymentConfig(@PathVariable UUID paymentId, @Valid @RequestBody PaymentConfigDto paymentDto) {
        var response = paymentConfigService.updatePaymentConfig(paymentId, paymentDto);
        return ResponseEntity.ok(ApiResponse.success("Update payment method successfully", response));
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<?>> deletePaymentConfig(@PathVariable UUID paymentId) {
        paymentConfigService.deletePaymentConfig(paymentId);
        return ResponseEntity.ok(ApiResponse.success("Delete payment method successfully", null));
    }
}
