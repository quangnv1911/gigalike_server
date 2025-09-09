FROM openjdk:17-jre-slim

WORKDIR /app

# Copy the JAR file
COPY target/config-server-*.jar app.jar

# Expose port
EXPOSE 8888

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8888/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
