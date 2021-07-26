FROM maven:3.8.1-adoptopendj-16 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY /../../../pom.xml /workspace
COPY /../../../src /workspace/src
RUN mvn -f pom.xml clean package

FROM adoptopenjdk:16-jre-hotspot
COPY --from=build /workspace/target/*.jar room.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-jar", "application.jar"]