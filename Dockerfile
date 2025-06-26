FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY target/job-posting-notifier.jar job-posting-notifier.jar
ENTRYPOINT ["java", "-jar", "job-posting-notifier.jar"]