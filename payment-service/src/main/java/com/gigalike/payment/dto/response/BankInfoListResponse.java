package com.gigalike.payment.dto.response;

import com.gigalike.payment.dto.data.BankInfoDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankInfoListResponse {
    String code;
    String desc;
    List<BankInfoDto> data;
}
