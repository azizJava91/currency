FROM openjdk:17-jdk-alpine
COPY target/home_task-0.0.1-SNAPSHOT.jar /app/myapp.jar
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]

