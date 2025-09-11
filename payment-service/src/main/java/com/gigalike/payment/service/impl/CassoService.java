package com.gigalike.payment.service.impl;

import com.gigalike.payment.dto.request.CassoWebhookRequest;
import com.gigalike.payment.dto.request.UpdateUserAmount;
import com.gigalike.payment.entity.Payment;
import com.gigalike.payment.feign.AuthClient;
import com.gigalike.payment.repository.PaymentRepository;
import com.gigalike.payment.service.ICassoService;
import com.gigalike.payment.service.IPaymentConfigService;
import com.gigalike.payment.service.IPaymentService;
import com.gigalike.shared.dto.ParsedPaymentDescription;
import com.gigalike.shared.exception.BusinessException;
import com.gigalike.shared.util.HttpUtil;
import com.gigalike.shared.util.PaymentUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CassoService implements ICassoService {
    AuthClient authClient;
    IPaymentService paymentService;
    PaymentRepository paymentRepository;

    @Override
    public void createCassoAccount() {

    }

    @Override
    public void getAllTransaction() {

    }

    @Override
    @Transactional
    public void handleCassoWebhook(CassoWebhookRequest cassoWebhookRequest) {
        var payment = paymentRepository.findByDescription(cassoWebhookRequest.getDescription());
        String description = cassoWebhookRequest.getDescription();

        ParsedPaymentDescription parsedPaymentDescription = PaymentUtil.parseDescription(description);
        if (!cassoWebhookRequest.getDescription().equals(payment.getDescription())) {
            throw new BusinessException(
                    "Descriptions of payment " + payment.getId() +
                            " and webhook " + cassoWebhookRequest.getId() + " do not match"
            );
        }
        UpdateUserAmount updateUserAmount = UpdateUserAmount.builder()
                .amount(cassoWebhookRequest.getAmount())
                .build();
        authClient.updateUserAmount(parsedPaymentDescription.getUserName(), updateUserAmount);
    }

    @Override
    public void validateCassoKey(String configKey, String signature) {
        if (!configKey.equals(signature)) {
            log.error("Invalid signature {}", signature);
            throw new BusinessException("Invalid casso signature");
        }
    }
}
