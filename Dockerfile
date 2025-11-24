# 1️⃣ Build stage
FROM gradle:8-jdk17 AS builder
WORKDIR /app

# Copy Gradle wrapper & config
COPY gradlew .
COPY gradlew.bat .
COPY gradle ./gradle

# Copy project files
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

# Build jar (skip tests)
RUN ./gradlew clean bootJar --no-daemon -x test

# 2️⃣ Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy jar from builder
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
