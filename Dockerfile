# ==============================================================================
# Multi-Stage Dockerfile for ARTIST HUB (Spring Boot / Java 17 / Maven)
# Designed for Render Web Service deployment
# ==============================================================================

# ------------------------------------------------------------------------------
# Stage 1: Build the Maven Spring Boot application
# ------------------------------------------------------------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Cache Maven dependencies by copying pom.xml first
COPY pom.xml .
RUN mvn dependency:go-offline -B || true

# Copy application source code
COPY src ./src

# Build production executable JAR (target/artist-hub-1.0.0.jar)
RUN mvn clean package -DskipTests

# ------------------------------------------------------------------------------
# Stage 2: Production JRE Runtime
# ------------------------------------------------------------------------------
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Create a non-root service user for container security
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Copy built artifact from builder stage
COPY --from=builder /app/target/artist-hub-1.0.0.jar app.jar

# Render assigns a dynamic port via the $PORT environment variable
ENV PORT=8080
EXPOSE 8080

# Launch application binding to Render's dynamic PORT
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
