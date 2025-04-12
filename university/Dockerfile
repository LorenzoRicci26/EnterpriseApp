# Stage 1: Build con Maven
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /app

# Copia il file pom.xml e scarica le dipendenze (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia l’intero sorgente e compila l’applicazione
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Immagine finale
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copia il jar generato dallo stage di build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Avvia l’applicazione; il comando consente a Spring Boot di leggere la variabile d'ambiente
ENTRYPOINT ["java", "-jar", "app.jar"]
