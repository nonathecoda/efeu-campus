FROM openjdk:13.0.2-jdk-slim

VOLUME /tmp

EXPOSE 8080

ADD target/process-management.jar app.jar

ENTRYPOINT ["java", "-jar","/app.jar"]