package com.gigalike.payment.controller;

import com.gigalike.payment.dto.data.PaymentConfigDto;
import com.gigalike.payment.dto.request.CassoWebhookRequest;
import com.gigalike.payment.service.ICassoService;
import com.gigalike.payment.service.IPaymentConfigService;
import com.gigalike.shared.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/casso")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CassoController {
    ICassoService cassoService;
    IPaymentConfigService paymentConfigService;

    @PostMapping("/{paymentConfigId}")
    public ResponseEntity<ApiResponse<?>> handleCassoWebhook(
            @PathVariable UUID paymentConfigId,
            @RequestHeader("X-Casso-Signature") String signature,
            @RequestBody CassoWebhookRequest cassoWebhookRequest) {
        var cassoDto = paymentConfigService.parseCassoConfig(paymentConfigId);
        cassoService.validateCassoKey(cassoDto.getKey(), signature);
        cassoService.handleCassoWebhook(cassoWebhookRequest);
        return ResponseEntity.ok(ApiResponse.success("Get all payment method successfully", null));
    }
}
