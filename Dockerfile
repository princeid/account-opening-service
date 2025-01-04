FROM openjdk:17-slim
WORKDIR /app
EXPOSE 8080
COPY target/*.jar /app/account-opening-service.jar
CMD ["java", "-jar", "account-opening-service.jar"]