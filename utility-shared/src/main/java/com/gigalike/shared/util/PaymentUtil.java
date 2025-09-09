package com.gigalike.shared.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class PaymentUtil {

    private static final SecureRandom random = new SecureRandom();
    private static final String TRANSACTION_PREFIX = "TXN";
    private static final String PAYMENT_PREFIX = "PAY";

    public String generateTransactionId() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomSuffix = String.format("%04d", random.nextInt(10000));
        return TRANSACTION_PREFIX + timestamp + randomSuffix;
    }

    public String generatePaymentReference() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomSuffix = String.format("%06d", random.nextInt(1000000));
        return PAYMENT_PREFIX + timestamp + randomSuffix;
    }

    public BigDecimal calculateServiceFee(BigDecimal amount, double feePercentage) {
        return amount.multiply(BigDecimal.valueOf(feePercentage / 100))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTotalAmount(BigDecimal baseAmount, BigDecimal serviceFee) {
        return baseAmount.add(serviceFee).setScale(2, RoundingMode.HALF_UP);
    }

    public boolean isAmountValid(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public Map<String, Object> createPaymentResponse(String transactionId, String status, BigDecimal amount) {
        Map<String, Object> response = new HashMap<>();
        response.put("transactionId", transactionId);
        response.put("status", status);
        response.put("amount", amount);
        response.put("timestamp", LocalDateTime.now());
        return response;
    }

    public String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 8) {
            return cardNumber;
        }
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

    public boolean validateCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            return false;
        }
        
        String cleaned = cardNumber.replaceAll("\\s+", "");
        
        // Basic length validation (13-19 digits for most cards)
        if (cleaned.length() < 13 || cleaned.length() > 19) {
            return false;
        }
        
        // Check if all characters are digits
        if (!cleaned.matches("\\d+")) {
            return false;
        }
        
        // Luhn algorithm validation
        return isValidLuhn(cleaned);
    }

    private boolean isValidLuhn(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit % 10) + 1;
                }
            }
            
            sum += digit;
            alternate = !alternate;
        }
        
        return (sum % 10) == 0;
    }

    public String getCardType(String cardNumber) {
        if (cardNumber == null) return "Unknown";
        
        String cleaned = cardNumber.replaceAll("\\s+", "");
        
        if (cleaned.startsWith("4")) return "Visa";
        if (cleaned.startsWith("5") || cleaned.startsWith("2")) return "Mastercard";
        if (cleaned.startsWith("3")) return "American Express";
        if (cleaned.startsWith("6")) return "Discover";
        
        return "Unknown";
    }

    public enum PaymentStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        CANCELLED,
        REFUNDED
    }

    public enum PaymentMethod {
        CREDIT_CARD,
        DEBIT_CARD,
        BANK_TRANSFER,
        E_WALLET,
        CRYPTOCURRENCY
    }
}
