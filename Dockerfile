FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ReactCoronaDashboard.jar
ENTRYPOINT ["java","-jar","/ReactCoronaDashboard.jar"]
