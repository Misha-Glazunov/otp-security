package com.otp.service;

import com.otp.dao.OtpCodeDao;
import com.otp.dao.OtpConfigDao;
import com.otp.model.OtpCode;
import com.otp.model.OtpConfig;
import com.otp.util.OtpGenerator;
import java.util.Optional;

public class OtpService {

    private final OtpCodeDao otpCodeDao;
    private final OtpConfigDao otpConfigDao;

    public OtpService() {
        this.otpCodeDao = new OtpCodeDao();
        this.otpConfigDao = new OtpConfigDao();
    }

    // Генерация OTP-кода
    public String generateOtp(String operationId) {
        Optional<OtpConfig> configOpt = otpConfigDao.getConfig();
        if (configOpt.isEmpty()) {
            throw new RuntimeException("OTP configuration not found.");
        }

        OtpConfig config = configOpt.get();
        String otpCode = OtpGenerator.generateOtp(config.getCodeLength());

        OtpCode otp = new OtpCode(operationId, otpCode, "ACTIVE");
        otpCodeDao.saveOtpCode(otp);

        return otpCode;
    }

    // Валидация OTP-кода
    public boolean validateOtp(int otpId, String code) {
        Optional<OtpCode> otpOpt = otpCodeDao.getOtpCode(otpId);
        if (otpOpt.isEmpty()) {
            return false; // OTP-код не найден
        }

        OtpCode otpCode = otpOpt.get();
        if (!otpCode.getCode().equals(code)) {
            return false; // Неверный код
        }

        // Проверяем, не истек ли срок действия
        if ("EXPIRED".equals(otpCode.getStatus())) {
            return false; // OTP-код истек
        }

        // Отмечаем код как использованный
        otpCodeDao.updateOtpCodeStatus(otpCode.getId(), "USED");
        return true;
    }
}