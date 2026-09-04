FROM eclipse-temurin:21-alpine
LABEL org.opencontainers.image.authors="Aaryan Gotad"
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]