FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY target/api-change-notifier.jar api-change-notifier.jar
ENTRYPOINT ["java", "-jar", "api-change-notifier.jar"]