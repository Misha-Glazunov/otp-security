package com.otp.model;

public class User {
    private String username;
    private String password;

    // Конструктор без параметров
    public User() {
    }

    // Конструктор с параметрами
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Геттер для username
    public String getUsername() {
        return username;
    }

    // Сеттер для username
    public void setUsername(String username) {
        this.username = username;
    }

    // Геттер для password
    public String getPassword() {
        return password;
    }

    // Сеттер для password
    public void setPassword(String password) {
        this.password = password;
    }

    // Переопределение метода toString() для удобства вывода
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}


