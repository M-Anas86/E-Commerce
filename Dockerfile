# Stage 1: Build the jar file
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the jar file
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/E-Commerce-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
