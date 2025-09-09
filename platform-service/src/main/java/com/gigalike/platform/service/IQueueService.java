package com.gigalike.platform.service;

public interface IQueueService {
    void resend(String exchange, String routingKey, String message);
    void resend(String exchange, String routingKey, String message, int priority) throws Exception;
}
