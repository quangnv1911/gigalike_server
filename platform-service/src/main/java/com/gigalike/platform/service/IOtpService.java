package com.gigalike.platform.service;

public interface IOtpService {

    String generateOtp(String email);
    boolean validateOtp(String otpInput, String email);
}
