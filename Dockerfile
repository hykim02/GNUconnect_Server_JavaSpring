FROM gradle:8.6-jdk21 AS builder
WORKDIR /build

# 그래들 파일이 변경되었을 때만 새롭게 의존패키지 다운로드 받게함.
COPY build.gradle settings.gradle /build/
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

COPY . /build
RUN gradle build -x test --parallel

FROM openjdk:21
WORKDIR /app

COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./app.jar

EXPOSE 5100

ENV ENVIRONMENT=production

#ENTRYPOINT ["java", "-jar", "app.jar"]