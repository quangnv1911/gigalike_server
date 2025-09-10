package com.gigalike.payment.controller;

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

//    @PostMapping("/")
//    public ResponseEntity<ApiResponse<?>> handleWebhook(@Valid @RequestBody RegisterRequest request) {
//        AuthResponse response = authService.register(request);
//        return ResponseEntity.ok(ApiResponse.success("User registered successfully", response));
//    }
}
