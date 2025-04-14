FROM gradle:7.6-jdk17 AS build
WORKDIR /home/gradle/project
COPY --chown=gradle:gradle . .

RUN gradle clean build -x test


FROM openjdk:17-jdk-alpine
WORKDIR /app
EXPOSE 8080
COPY --from=build /home/gradle/project/build/libs/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "./app.jar"]
