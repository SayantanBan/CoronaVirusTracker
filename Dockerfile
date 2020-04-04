FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY build/libs/ReactCoronaDashboard-0.0.1-SNAPSHOT.jar ReactCoronaDashboard.jar
ENTRYPOINT ["java","-jar","/ReactCoronaDashboard.jar"]
