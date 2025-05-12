package com.otp.util;

import com.otp.service.EmailNotificationService;

public class EmailService {

    private final EmailNotificationService emailNotificationService;

    public EmailService() {
        this.emailNotificationService = new EmailNotificationService();
    }

    // Метод для отправки OTP-кода через Email
    public void sendOtpEmail(String otpCode) {
        String recipient = "user@example.com";  // Здесь будет логика получения email адреса
        emailNotificationService.sendCode(recipient, otpCode);
    }
}