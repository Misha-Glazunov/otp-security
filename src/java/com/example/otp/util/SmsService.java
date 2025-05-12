package com.otp.util;

public class SmsService {

    private final SmppClient smppClient;

    public SmsService() {
        this.smppClient = new SmppClient();
    }

    // Метод для отправки OTP-кода через SMS
    public void sendOtpSms(String otpCode) {
        String recipient = "recipientPhoneNumber";  // Здесь будет логика получения номера телефона
        smppClient.sendCode(recipient, otpCode);
    }
}