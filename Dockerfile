FROM openjdk:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
#COPY ./application.properties /app/application.properties
#ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/application.properties"]