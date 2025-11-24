# Multi-stage build
# First stage: Build the application
FROM openjdk:17-jdk-slim as builder

WORKDIR /app

# Copy build files
COPY build.gradle settings.gradle ./

# Copy source code
COPY src ./src

# Build the application
RUN ./gradlew clean build -x test

# Second stage: Create the runtime image
FROM openjdk:17-jre-slim

WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/ramen-ordering-platform-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]