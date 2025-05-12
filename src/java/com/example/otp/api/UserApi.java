package com.otp.api;

import com.otp.service.UserService;
import com.otp.util.JsonUtil;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class UserApi {

    private final UserService userService;

    public UserApi() {
        this.userService = new UserService();
    }

    // API для регистрации пользователя
    public void registerUser(HttpExchange exchange) throws IOException {
        String requestBody = JsonUtil.readRequestBody(exchange);
        // Предполагаем, что тело запроса содержит json с полями username, password, role
        String username = JsonUtil.getJsonValue(requestBody, "username");
        String password = JsonUtil.getJsonValue(requestBody, "password");
        String role = JsonUtil.getJsonValue(requestBody, "role");

        boolean success = userService.registerUser(username, password, role);
        String response = success ? "User registered successfully" : "Username already taken";

        exchange.sendResponseHeaders(success ? 200 : 400, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    // API для логина
    public void loginUser(HttpExchange exchange) throws IOException {
        String requestBody = JsonUtil.readRequestBody(exchange);
        String username = JsonUtil.getJsonValue(requestBody, "username");
        String password = JsonUtil.getJsonValue(requestBody, "password");

        boolean success = userService.authenticateUser(username, password);
        String response = success ? "Authentication successful" : "Invalid credentials";

        exchange.sendResponseHeaders(success ? 200 : 400, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new URI("/"), 0);
        server.createContext("/register", new UserApi()::registerUser);
        server.createContext("/login", new UserApi()::loginUser);
        server.start();
    }
}