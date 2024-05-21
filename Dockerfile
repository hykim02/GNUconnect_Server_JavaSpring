FROM openjdk:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 6000
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.config.location=file:/src/main/resources/application.properties"]