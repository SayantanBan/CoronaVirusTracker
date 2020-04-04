FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY build/libs/ReactCoronaDashboard.jar ReactCoronaDashboard.jar
ENTRYPOINT ["java","-jar","/ReactCoronaDashboard.jar"]
