FROM adoptopenjdk/openjdk11:latest

ADD . /tmp

RUN cd /tmp && cp /tmp/target/*.jar /app.jar && rm -rf /tmp/*

ENTRYPOINT ["java", "-Djava.security.edg=file:/dev/./urandom","-jar","/app.jar"]