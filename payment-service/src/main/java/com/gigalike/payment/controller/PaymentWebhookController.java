package com.gigalike.payment.controller;

import com.gigalike.payment.dto.request.CassoWebhookRequest;
import com.gigalike.payment.service.ICassoService;
import com.gigalike.payment.service.IPaymentConfigService;
import com.gigalike.shared.dto.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/casso")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentWebhookController {
    ICassoService cassoService;
    IPaymentConfigService paymentConfigService;

    @PostMapping("/casso/{paymentConfigId}")
    public ResponseEntity<ApiResponse<?>> handleCassoWebhook(
            @PathVariable UUID paymentConfigId,
            @RequestHeader("X-Casso-Signature") String signature,
            @RequestBody CassoWebhookRequest cassoWebhookRequest) {
        var cassoDto = paymentConfigService.parseCassoConfig(paymentConfigId);
        cassoService.validateCassoKey(cassoDto.getKey(), signature);
        cassoService.handleCassoWebhook(cassoWebhookRequest);
        return ResponseEntity.ok(ApiResponse.success("Handle casso webhook successfully", null));
    }

    @PostMapping("/cusstom/{paymentConfigId}")
    public ResponseEntity<ApiResponse<?>> handleCustomWebhook(
            @PathVariable UUID paymentConfigId,
            @RequestHeader("Key") String signature,
            @RequestBody CassoWebhookRequest cassoWebhookRequest) {
        var cassoDto = paymentConfigService.parseCassoConfig(paymentConfigId);
        cassoService.validateCassoKey(cassoDto.getKey(), signature);
        cassoService.handleCassoWebhook(cassoWebhookRequest);
        return ResponseEntity.ok(ApiResponse.success("Handle custom webhook successfully", null));
    }
}
