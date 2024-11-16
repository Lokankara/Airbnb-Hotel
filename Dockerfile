# Build stage
FROM maven:3.9.8-eclipse-temurin-21 AS build

COPY . .
RUN mvn clean package -DskipTests -Dmaven

# Run stage	
FROM openjdk:21-slim

COPY --from=build /target/hotel-0.0.1-SNAPSHOT.jar hotel.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","hotel.jar"]
