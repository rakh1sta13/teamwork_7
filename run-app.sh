#!/bin/bash

# Script to run the Ramen Ordering Platform application

echo "Starting Ramen Ordering Platform..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Java is not installed. Please install Java 17 or higher."
    exit 1
fi

# Check Java version
java_version=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
if [[ "$java_version" < "17" ]]; then
    echo "Java 17 or higher is required. Current version: $java_version"
    exit 1
fi

echo "Java version is compatible."

# Check if PostgreSQL is running
if ! pg_isready -h localhost -p 5432 > /dev/null 2>&1; then
    echo "PostgreSQL is not running. Please start PostgreSQL."
    echo "On Ubuntu/Debian: sudo systemctl start postgresql"
    echo "On CentOS/RHEL: sudo systemctl start postgresql"
    echo "On macOS: brew services start postgresql"
    exit 1
fi

echo "PostgreSQL is running."

# Build the application
echo "Building the application..."
./gradlew clean build -x test

if [ $? -eq 0 ]; then
    echo "Build successful!"
    echo "Starting the application..."
    java -jar build/libs/ramen-ordering-platform-0.0.1-SNAPSHOT.jar
else
    echo "Build failed!"
    exit 1
fi