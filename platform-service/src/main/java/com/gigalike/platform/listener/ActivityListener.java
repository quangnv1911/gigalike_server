package com.gigalike.platform.listener;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigalike.platform.dto.ActivityDto;
import com.gigalike.platform.service.IActivityService;
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
public class ActivityListener {
    IActivityService activityService;
    IQueueService queueService;

    @ActivitySyncListener(QueueConstant.ACTIVITY_QUEUE)
    public void handleMessage(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ActivityDto request = mapper.readValue(message, ActivityDto.class);
            activityService.createActivity(request);
        } catch (Exception ex) {
            log.error("Create activity sync Exception: ", ex);
            String messageFailed = CommonUtil.messageFailed(message, ExchangeConstant.NOTIFY_FAIL_QUEUE, QueueConstant.ACTIVITY_QUEUE, ExceptionUtils.getStackTrace(ex));
            queueService.resend(ExchangeConstant.ACTIVITY, QueueConstant.ACTIVITY_QUEUE, messageFailed);
        }
    }
}
