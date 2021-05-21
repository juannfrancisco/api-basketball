FROM openjdk:8-jdk-alpine

ADD . /tmp

RUN cd /tmp && ls -ltr && cp /tmp/target/*.jar /app.jar && rm -rf /tmp

ENTRYPOINT ["java", "-Djava.security.edg=file:/dev/./urandom","-jar","/app.jar"]