# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build

COPY . .
RUN mvn clean package -DskipTests -Dmaven

# Run stage	
FROM openjdk:17-slim

COPY --from=build /target/hotel-0.0.1-SNAPSHOT.jar hotel.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","hotel.jar"]
