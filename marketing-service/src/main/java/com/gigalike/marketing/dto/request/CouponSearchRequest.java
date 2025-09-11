package com.gigalike.marketing.dto.request;

import com.gigalike.shared.dto.PageRequestCustom;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponSearchRequest extends PageRequestCustom {
    String name;
    String code;
}
