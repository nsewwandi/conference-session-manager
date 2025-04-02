FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/ConferenceSessionManager-1.0-SNAPSHOT.jar app.jar
COPY config.yml config.yml

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar", "server", "config.yml"]