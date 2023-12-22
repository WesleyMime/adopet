FROM openjdk:18-jdk-alpine

# Creates a non-root user and group named "spring" inside the container.
# This is a security best practice to avoid running the application as the root user.
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-DPOSTGRES_URL=db:5432/postgres","-DPOSTGRES_USERNAME=postgres","-DPOSTGRES_PASSWORD=adopet","-jar","/app.jar"]