package com.gigalike.platform.service.impl;

import com.gigalike.platform.config.OtpProperties;
import com.gigalike.platform.service.IOtpService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OtpService implements IOtpService {
    OtpProperties otpProperties;

    RedisTemplate<String, String> keyDbTemplate;
    /**
     * Sinh OTP và lưu vào Redis với key là OTP:{email} và TTL 5 phút.
     *
     * @param email Email của người dùng
     * @return OTP đã sinh (ví dụ: gửi qua email)
     */
    @Override
    public String generateOtp(String email) {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < otpProperties.getLength(); i++) {
            otp.append(random.nextInt(10));  // Sinh ra một chữ số ngẫu nhiên từ 0 đến 9
        }
        String key = "OTP:" + email;
        keyDbTemplate.opsForValue().set(key, otp.toString(), otpProperties.getExpireTime(), TimeUnit.HOURS);
        return otp.toString();
    }

    @Override
    public boolean validateOtp(String otpInput, String email) {
        String key = "OTP:" + email;
        String storedOtp = keyDbTemplate.opsForValue().get(key);
        if (storedOtp != null && storedOtp.equals(otpInput)) {
            // Sau khi xác thực, có thể xóa OTP khỏi Redis
            keyDbTemplate.delete(key);
            return true;
        }
        return false;
    }
}
