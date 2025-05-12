package com.otp.api;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class HttpServerInitializer {
    private static final Logger logger = Logger.getLogger(HttpServerInitializer.class.getName());

    public static void startServer(int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            // Здесь позже добавим обработчики
            logger.info("HTTP server started on port " + port);
            server.start();
        } catch (IOException e) {
            throw new RuntimeException("Failed to start HTTP server", e);
        }
    }
}