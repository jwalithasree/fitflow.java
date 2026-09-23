# Stage 1: Build stage
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copy project source files
COPY FitFlow/ ./FitFlow/

# Create bin directory for compiled classes
RUN mkdir -p FitFlow/bin

# Compile all Java source files
RUN javac -d FitFlow/bin $(find FitFlow/src -name "*.java")

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy compiled classes and assets
COPY --from=build /app/FitFlow ./FitFlow

# Expose default port
EXPOSE 8080

# Run FitFlow
CMD ["java", "-cp", "FitFlow/bin", "Main"]
