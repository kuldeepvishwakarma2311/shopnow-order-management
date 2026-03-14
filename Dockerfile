FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY order-app/target/docker/shopnow-order-management.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
