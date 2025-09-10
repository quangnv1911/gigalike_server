package com.gigalike.shared.util;

import com.gigalike.shared.dto.VietQrConfigDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class PaymentUtil {
    private static final String vietQrTemplate = "https://img.vietqr.io/image/<BANK_ID>-<ACCOUNT_NO>-<TEMPLATE>.png?amount=<AMOUNT>&addInfo=<DESCRIPTION>&accountName=<ACCOUNT_NAME>";
    private static final String randomChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final String urlGetBankInfo = "https://api.vietqr.io/v2/banks";
    public static String generatePaymentString(String userName) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(userName);

        // Thêm 6 ký tự ngẫu nhiên
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(randomChars.length());
            sb.append(randomChars.charAt(index));
        }
        return sb.toString();
    }

    public static String generateQrUrl(VietQrConfigDto config, BigDecimal amount) {
        Map<String, String> values = Map.of(
                "<BANK_ID>", config.getBankId(),
                "<ACCOUNT_NO>", config.getAccountNo(),
                "<TEMPLATE>", config.getTemplate(),
                "<ACCOUNT_NAME>", encodeValue(config.getAccountName()),
                "<DESCRIPTION>", encodeValue(config.getDescription()),
                "<AMOUNT>", amount.toPlainString()
        );

        String result = vietQrTemplate;
        for (Map.Entry<String, String> entry : values.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }

        return result;
    }

    // Nếu có ký tự đặc biệt, encode để URL hợp lệ
    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return value;
        }
    }

}
