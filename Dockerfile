# ============================================
# Stage 1: Build the JAR with Maven
# ============================================
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
# Copy pom.xml first (for better caching)
COPY pom.xml .
# Download dependencies (cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B
# Copy source code
COPY src ./src
# Build the application
RUN mvn clean package -DskipTests

# ============================================
# Stage 2: Run the JAR in a slim runtime image
# ============================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]