FROM openjdk:11-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ReactCoronaDashboard.jar
ENTRYPOINT ["java","-jar","/ReactCoronaDashboard.jar"]
