FROM openjdk:21
ARG JAR_FILE=build/libs/Jinus-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ARG ENV_FILE=src/main/resources/application.properties
COPY ${ENV_FILE} application.properties
EXPOSE 5100
ENTRYPOINT ["java","-jar","/app.jar"]