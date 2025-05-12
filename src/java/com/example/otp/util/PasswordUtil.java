package com.otp.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Хеширование пароля
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Проверка пароля
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}