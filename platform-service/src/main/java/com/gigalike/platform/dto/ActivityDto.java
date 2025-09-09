package com.gigalike.platform.dto;

import com.gigalike.shared.constant.ActionType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityDto {
    String metaData;
    String status;
    ActionType type;
}
