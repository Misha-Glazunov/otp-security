package com.otp.util;

import com.otp.dao.OtpCodeDao;
import com.otp.model.OtpCode;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OtpExpiryScheduler {

    private final OtpCodeDao otpCodeDao;

    public OtpExpiryScheduler() {
        this.otpCodeDao = new OtpCodeDao();
    }

    // Планировщик для обновления статусов OTP-кодов
    public void startOtpExpiryTask() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            List<OtpCode> otpCodes = otpCodeDao.getAllActiveOtpCodes();
            for (OtpCode otpCode : otpCodes) {
                if (otpCode.isExpired()) {
                    otpCodeDao.updateOtpCodeStatus(otpCode.getId(), "EXPIRED");
                    System.out.println("OTP code expired: " + otpCode.getCode());
                }
            }
        }, 0, 1, TimeUnit.MINUTES);  // Периодичность задачи — каждую минуту
    }
}