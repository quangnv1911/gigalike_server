package com.gigalike.platform.service.impl;

import com.gigalike.platform.service.ITelegramService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TelegramService implements ITelegramService {
    @NonFinal
    @Value("${notification.telegram.token}")
    String teleToken;

    @NonFinal
    @Value("${notification.telegram.group-id}")
    String chatId;


    @Override
    public void sendMessage(String message) {

    }
}
