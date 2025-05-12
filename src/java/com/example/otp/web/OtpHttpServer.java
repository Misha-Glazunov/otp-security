package com.otp.web;

import com.otp.service.OtpService;
import com.otp.service.AdminService;
import com.otp.model.User;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class OtpHttpServer {

    private static OtpService otpService;
    private static AdminService adminService;

    public static void main(String[] args) throws Exception {
        otpService = new OtpService();
        adminService = new AdminService();

        // Создание и настройка HTTP-сервера
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Обработка запросов на генерацию OTP-кода
        server.createContext("/generateOtp", new OtpHandler());

        // Администрирование: изменение конфигурации и удаление пользователя
        server.createContext("/admin/changeConfig", new AdminConfigHandler());
        server.createContext("/admin/deleteUser", new AdminDeleteUserHandler());

        // Запуск сервера
        server.start();
        System.out.println("Server started on port 8080");
    }

    // Обработчик для генерации OTP-кодов
    static class OtpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "OTP code generated successfully";
            String operationId = getQueryParam(exchange, "operationId");

            // Генерация OTP-кода
            String otpCode = otpService.generateOtp(operationId);

            // Ответ пользователю
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    // Обработчик для изменения конфигурации OTP
    static class AdminConfigHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "OTP configuration updated successfully";
            // Извлечение параметров конфигурации из запроса
            String config = getQueryParam(exchange, "config");

            // Логика обновления конфигурации
            boolean isUpdated = adminService.changeOtpConfig(Integer.parseInt(config), 300);  // Пример

            // Ответ администратору
            exchange.sendResponseHeaders(isUpdated ? 200 : 400, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    // Обработчик для удаления пользователя
    static class AdminDeleteUserHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "User deleted successfully";
            String username = getQueryParam(exchange, "username");

            // Логика удаления пользователя
            boolean isDeleted = adminService.deleteUser(username);

            // Ответ администратору
            exchange.sendResponseHeaders(isDeleted ? 200 : 400, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }