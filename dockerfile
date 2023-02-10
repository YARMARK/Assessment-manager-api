FROM openjdk:11
VOLUME /main-app
ADD /target/googleTest-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8085