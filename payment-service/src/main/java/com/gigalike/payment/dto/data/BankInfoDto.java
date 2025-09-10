package com.gigalike.payment.dto.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankInfoDto {
    private int id;
    private String name;
    private String code;
    private String bin;
    private String shortName;
    private String logo;
    private int transferSupported;
    private int lookupSupported;

    @JsonProperty("short_name")
    private String shortNameAlias;

    private int support;

    @JsonProperty("isTransfer")
    private int isTransfer;

    @JsonProperty("swift_code")
    private String swiftCode;
}
