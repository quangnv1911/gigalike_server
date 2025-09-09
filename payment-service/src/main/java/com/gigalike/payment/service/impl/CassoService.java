package com.gigalike.payment.service.impl;

import com.gigalike.payment.dto.request.CassoWebhookRequest;
import com.gigalike.payment.service.ICassoService;
import com.gigalike.payment.service.IPaymentConfigService;
import com.gigalike.payment.service.IPaymentService;
import com.gigalike.shared.exception.BusinessException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CassoService implements ICassoService {
    IPaymentService paymentService;

    @Override
    public void createCassoAccount() {

    }

    @Override
    public void getAllTransaction() {

    }

    @Override
    public void handleCassoWebhook(CassoWebhookRequest cassoWebhookRequest) {

    }

    @Override
    public void validateCassoKey(String configKey, String signature) {
        if (!configKey.equals(signature)) {
            log.error("Invalid signature {}", signature);
            throw new BusinessException("Invalid casso signature");
        }
    }
}
