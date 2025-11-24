# 1️⃣ Build stage
FROM gradle:8.14-jdk17 AS builder
WORKDIR /app

# Copy Gradle wrapper files
COPY gradlew .
COPY gradlew.bat .
COPY gradle ./gradle

# Make gradlew executable
RUN chmod +x ./gradlew

# Copy project files
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

# Build the project (download dependencies and compile without running tests)
RUN ./gradlew clean build -x test --no-daemon
# The bootJar task is already executed as part of build, so we don't need to run it separately

# 2️⃣ Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy jar from builder
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
