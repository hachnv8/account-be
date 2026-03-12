# Chỉ cần runtime vì build Maven đã thực hiện ở Jenkins pipeline
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy file jar đã được build bởi Maven ở bước trước
COPY target/*.jar app.jar

# Port 8085 khớp với server.port=8085 trong application.properties
EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]
