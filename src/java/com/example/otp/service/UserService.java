package com.otp.service;

import com.otp.dao.UserDao;
import com.otp.model.User;
import com.otp.util.PasswordUtil;
import java.util.Optional;

public class UserService {

    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    // Регистрация нового пользователя
    public boolean registerUser(String username, String password, String role) {
        // Проверяем, не существует ли уже пользователь с таким логином
        Optional<User> existingUser = userDao.findByUsername(username);
        if (existingUser.isPresent()) {
            return false; // Пользователь уже существует
        }

        // Хешируем пароль перед сохранением
        String passwordHash = PasswordUtil.hashPassword(password);

        // Создаем нового пользователя
        User newUser = new User(username, passwordHash, role);
        return userDao.createUser(newUser);
    }

    // Аутентификация пользователя
    public boolean authenticateUser(String username, String password) {
        Optional<User> userOpt = userDao.findByUsername(username);
        if (userOpt.isEmpty()) {
            return false; // Пользователь не найден
        }

        User user = userOpt.get();
        // Проверяем, совпадает ли хешированный пароль
        return PasswordUtil.verifyPassword(password, user.getPasswordHash());
    }
}