package com.gigalike.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CollaboratorViewResponse {
    String userName;

    String email;

    String domain;

    BigDecimal totalBalance;
}
