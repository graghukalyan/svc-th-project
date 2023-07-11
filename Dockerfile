FROM openjdk:15-jdk-alpine
LABEL authors="prep_user"
MAINTAINER "prep_user"
COPY build/libs/processor-service-0.0.1-SNAPSHOT.jar test-server-1.0.0.jar
ENTRYPOINT ["java","-jar","/test-server-1.0.0.jar"]