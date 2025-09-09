package com.gigalike.platform.service;

public interface INotificationService {
    void sendTelegramMessage(String message);
    void sendEmailMessage(String message);
    void senDiscordMessage(String message);
}
