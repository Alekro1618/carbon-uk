FROM eclipse-temurin:24-jdk AS build
WORKDIR /app

COPY gradlew .
COPY gradle ./gradle

RUN chmod +x gradlew

# Build the JAR using the wrapper
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]