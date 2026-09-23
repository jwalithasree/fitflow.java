# Stage 1: Build stage
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

COPY src/ /app/src/
COPY web/ /app/web/
COPY css/ /app/css/
COPY js/ /app/js/

RUN mkdir -p /app/bin
RUN javac -d /app/bin $(find /app/src -name "*.java")

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY --from=build /app /app

EXPOSE 8080

CMD ["java", "-cp", "/app/bin", "Main"]
