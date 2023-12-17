FROM maven:3.8-openjdk-17 AS build
COPY ..
RUN mvn clean package -DskipTest

FROM openjdk:17-ea-28-jdk-slim
COPY ==from=build /target/tuyageWeb--0.0.1-SNAPSHOT.jar tuyageWeb.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","tuyageWeb.jar"]
LABEL authors="user"

ENTRYPOINT ["top", "-b"]