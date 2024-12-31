FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/account-opening-service-0.0.1-SNAPSHOT.jar /app/account-opening-service.jar
EXPOSE 8080
CMD ["java", "-jar", "account-opening-service.jar"]