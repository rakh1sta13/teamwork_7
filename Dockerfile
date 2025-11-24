# 1️⃣ Build stage
FROM gradle:8.14-jdk17 AS builder
WORKDIR /app

# Copy all project files
COPY . .

# Use the installed Gradle instead of the wrapper to avoid wrapper class issues
RUN gradle clean build -x test --no-daemon

# 2️⃣ Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy jar from builder
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
