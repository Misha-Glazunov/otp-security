package com.otp.util;

import java.security.SecureRandom;

public class OtpGenerator {

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    // Генерация OTP-кода
    public static String generateOtp(int length) {
        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            otp.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return otp.toString();
    }
}