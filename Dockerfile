FROM openjdk:11-alpine
VOLUME /tmp
ADD build/libs/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
