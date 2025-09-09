package com.gigalike.platform.listener;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigalike.platform.dto.MessageDto;
import com.gigalike.platform.service.INotificationService;
import com.gigalike.platform.service.IQueueService;
import com.gigalike.shared.constant.ExchangeConstant;
import com.gigalike.shared.constant.QueueConstant;
import com.gigalike.shared.util.CommonUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;

@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotifyListener {
    INotificationService notificationService;
    IQueueService queueService;

    @NotifySyncListener(QueueConstant.NOTIFY_QUEUE)
    public void handleMessage(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            MessageDto request = mapper.readValue(message, MessageDto.class);
            switch (request.getAction()) {
                case QueueConstant.TELEGRAM_QUEUE:
                    notificationService.sendTelegramMessage(request.getMessage());
                    break;
                case QueueConstant.EMAIL_QUEUE:
                    notificationService.sendEmailMessage(request.getMessage());
                    break;
                case QueueConstant.DISCORD_QUEUE:
                    notificationService.senDiscordMessage(request.getMessage());
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            log.error("Notify sync Exception: ", ex);
            String messageFailed = CommonUtil.messageFailed(message, ExchangeConstant.NOTIFY_FAIL_QUEUE, QueueConstant.NOTIFY_QUEUE, ExceptionUtils.getStackTrace(ex));
            queueService.resend(ExchangeConstant.NOTIFY, QueueConstant.NOTIFY_QUEUE, messageFailed);
        }
    }
}
