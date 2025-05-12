package com.otp.util;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;

public class TelegramService {

    private final String telegramApiUrl = "https://api.telegram.org/botYOUR_BOT_TOKEN/sendMessage";
    private final String chatId = "yourChatId";  // Получить chatId через Telegram API

    // Метод для отправки OTP-кода через Telegram
    public void sendOtpTelegram(String otpCode) {
        String message = String.format("Your OTP code is: %s", otpCode);
        String url = String.format("%s?chat_id=%s&text=%s", telegramApiUrl, chatId, message);

        sendTelegramRequest(url);
    }

    private void sendTelegramRequest(String url) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    System.err.println("Telegram API error. Status code: " + statusCode);
                } else {
                    System.out.println("Telegram message sent successfully");
                }
            }
        } catch (IOException e) {
            System.err.println("Error sending Telegram message: " + e.getMessage());
        }
    }
}