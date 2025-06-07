# Use a lightweight Java runtime image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file built by Maven
COPY target/taskapi-1.0.0.jar app.jar


# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
