# Этап 1: Сборка проекта
FROM gradle:7.6.0-jdk17 AS builder

# Устанавливаем рабочую директорию для сборки
WORKDIR /app

# Копируем все Gradle файлы и исходный код
COPY . .

# Выполняем сборку проекта
RUN ./gradlew build -x test

# Этап 2: Запуск приложения
FROM eclipse-temurin:17

# Устанавливаем рабочую директорию для приложения
WORKDIR /app

# Копируем скомпилированный .jar файл из предыдущего этапа
COPY --from=builder /app/build/libs/*.jar app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
