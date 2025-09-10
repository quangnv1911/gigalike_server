package com.gigalike.marketing.dto.request;

import com.gigalike.shared.dto.PageRequestCustom;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CouponSearchRequest extends PageRequestCustom {
    String name;
    String code;
}
