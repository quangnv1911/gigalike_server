package com.gigalike.platform.service.impl;

import com.gigalike.platform.service.INotificationService;
import com.gigalike.platform.service.IQueueService;

public class QueueService implements IQueueService {

    @Override
    public void resend(String exchange, String routingKey, String message) {

    }

    @Override
    public void resend(String exchange, String routingKey, String message, int priority) throws Exception {

    }
}
