#!/bin/bash

# Script to set up and run the Online Food Ordering Platform

echo "Setting up Online Food Ordering Platform..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Java is not installed. Please install Java 17 or higher."
    echo "On Ubuntu/Debian: sudo apt install openjdk-17-jdk"
    echo "On CentOS/RHEL: sudo yum install java-17-openjdk-devel"
    echo "On macOS: brew install openjdk@17"
    exit 1
fi

# Check Java version
java_version=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
if [[ "$java_version" < "17" ]]; then
    echo "Java 17 or higher is required. Current version: $java_version"
    exit 1
fi

# Check if PostgreSQL is installed
if ! command -v psql &> /dev/null; then
    echo "PostgreSQL is not installed. Please install PostgreSQL."
    echo "On Ubuntu/Debian: sudo apt install postgresql postgresql-contrib"
    echo "On CentOS/RHEL: sudo yum install postgresql-server postgresql-contrib"
    echo "On macOS: brew install postgresql"
    exit 1
fi

echo "Java and PostgreSQL are installed."

# Create the database if it doesn't exist
echo "Setting up database..."
sudo -u postgres psql -c "SELECT 1 FROM pg_database WHERE datname='food_ordering'" | grep -q 1 || \
    sudo -u postgres createdb -O postgres food_ordering

echo "Database setup complete."

# Build and run the application
echo "Building the application..."
./gradlew clean build -x test

if [ $? -eq 0 ]; then
    echo "Build successful!"
    echo "Starting the application..."
    ./gradlew bootRun
else
    echo "Build failed!"
    exit 1
fi