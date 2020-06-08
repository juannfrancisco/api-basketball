FROM openjdk:8-jdk-alpine

COPY target/*.jar" app.jar

ENTRYPOINT ["java", "-Djava.security.edg=file:/dev/./urandom","-jar","/app.jar"]