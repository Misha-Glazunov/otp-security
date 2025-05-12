package com.otp;

import com.otp.api.HttpServerInitializer;

public class App {
    public static void main(String[] args) {
        HttpServerInitializer.startServer(8080);
    }
}