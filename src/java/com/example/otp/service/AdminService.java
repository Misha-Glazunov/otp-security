package com.otp.service;

import com.otp.dao.UserDao;
import com.otp.dao.OtpCodeDao;
import com.otp.model.User;
import com.otp.model.OtpCode;
import com.otp.model.OtpConfig;

public class AdminService {

    private final UserDao userDao;
    private final OtpCodeDao otpCodeDao;
    private final OtpConfigDao otpConfigDao;

    public AdminService() {
        this.userDao = new UserDao();
        this.otpCodeDao = new OtpCodeDao();
        this.otpConfigDao = new OtpConfigDao();
    }

    // Смена конфигурации OTP-кодов
    public boolean changeOtpConfig(int codeLength, int ttl) {
        OtpConfig config = new OtpConfig(codeLength, ttl);
        return otpConfigDao.updateConfig(config);
    }

    // Удаление пользователя и его OTP-кодов
    public boolean deleteUser(String username) {
        // Удаление OTP-кодов пользователя
        otpCodeDao.deleteOtpCodesByUser(username);

        // Удаление пользователя
        return userDao.deleteUser(username);
    }
}