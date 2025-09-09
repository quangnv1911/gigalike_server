package com.gigalike.payment.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CassoWebhookRequest {
    //Mã định danh duy nhất của giao dịch (Casso quy định)
    Long id;
    // Mã giao dịch từ phía ngân hàng
    String reference;
    // Nội dung giao dịch
    String description;
    // Số tiền giao dịch
    BigDecimal amount;
    // Số dư sau giao dịch
    BigDecimal runningBalance;
    // Thời gian giao dịch
    Date transactionDateTime;
    // Số tài khoản mà giao dịch thuộc về
    String accountNumber;
    // Tên ngân hàng
    String bankName;
    // Viết tắt tên ngân hàng
    String bankAbbreviation;
    // Tài khoản ảo
    String virtualAccountNumber;
    // Tên tài khoản ảo
    String virtualAccountName;
    // Tên tài khoản đối ứng
    String counterAccountName;
    // Tài khoản đối ứng
    String counterAccountNumber;
    // Mã ngân hàng đối ứng
    String counterAccountBankId;
    // Tên ngân hàng đối ứng
    String counterAccountBankName;
}
