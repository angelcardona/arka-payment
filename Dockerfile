FROM gradle:8.5-jdk21-alpine AS build
WORKDIR /app


COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle


RUN gradle dependencies --no-daemon


COPY src /app/src


RUN gradle clean build -x test --no-daemon

FROM openjdk:21-jdk-slim


RUN apt-get update && apt-get install -y ca-certificates && apt-get clean

WORKDIR /app
EXPOSE 8083


COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]