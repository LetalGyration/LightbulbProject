FROM maven:3.8.1-adoptopenjdk-16 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -DskipTests=true -f pom.xml clean package

FROM adoptopenjdk:16-jre-hotspot
COPY --from=build /workspace/target/*.jar room.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-jar", "room.jar"]