# Étape 1 : Utiliser une image Maven pour construire le projet
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copier le pom.xml et télécharger les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code source et compiler le projet
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Exécuter l'application avec une image JDK légère
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copier le JAR généré depuis la phase de build
COPY --from=builder /app/target/*.jar app.jar

# Exposer le port utilisé par Spring Boot
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
