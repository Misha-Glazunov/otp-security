# Используем базовый образ с JDK
FROM openjdk:17-jdk-slim

# Рабочая директория внутри контейнера
WORKDIR /app

# Копируем собранный jar из target/
COPY target/otp-backend-1.0-SNAPSHOT.jar /app/app.jar

# Команда запуска
CMD ["java", "-jar", "/app/app.jar"]
