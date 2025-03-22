# Use an official JDK image as a base
FROM openjdk:17-jdk-slim

# Set working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/food-order-system.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
