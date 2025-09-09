package com.gigalike.payment.dto.response;

import com.gigalike.payment.dto.data.PaymentDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaymentMethodResponse {
    List<PaymentDto> payments;
}
