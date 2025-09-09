package com.gigalike.payment.service;

import com.gigalike.payment.dto.request.CassoWebhookRequest;

public interface ICassoService {
    void createCassoAccount();
    void getAllTransaction();
    void handleCassoWebhook(CassoWebhookRequest cassoWebhookRequest);
    void validateCassoKey(String configKey, String signature);
}
