FROM gradle:8.7.0-jdk17 as builder

WORKDIR /app

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle.properties .

COPY src src

RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]