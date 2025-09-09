package com.gigalike.platform.service.impl;

import com.gigalike.platform.dto.ActivityDto;
import com.gigalike.platform.entity.Activity;
import com.gigalike.platform.repository.ActivityRepository;
import com.gigalike.platform.service.IActivityService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActivityService implements IActivityService {
    ActivityRepository activityRepository;

    @Override
    public void createActivity(ActivityDto activity) {
        Activity newActivity = Activity.builder()
                .type(activity.getType())
                .metaData(activity.getMetaData())
                .status(activity.getStatus())
                .build();
        activityRepository.save(newActivity);
    }
}
