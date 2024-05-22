FROM gradle:8.6-jdk21 as builder
WORKDIR /build

COPY . /build
RUN gradle build -x test --parallel

FROM openjdk:21
WORKDIR /app

COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./app.jar

EXPOSE 5100

ENTRYPOINT ["java", "-jar", "app.jar"]