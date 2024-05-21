FROM openjdk:21
VOLUME /tmp
ARG JAR_FILE=build/libs/Jinus-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 5100
ENTRYPOINT ["java","-jar","/app.jar"]