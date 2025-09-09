FROM maven:3.9.8-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the JAR file
COPY --from=builder target/platform-service-*.jar app.jar

# Expose port
EXPOSE 8086

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8086/api/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
