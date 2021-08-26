FROM gradle:7.2.0-jdk11 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY build.gradle /workspace
COPY src /workspace/src
RUN gradle clean build --no-daemon -x test

FROM openjdk:11
COPY --from=build /workspace/build/libs/*.jar room.jar
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-jar", "room.jar"]