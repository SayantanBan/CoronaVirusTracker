FROM openjdk:11
FROM openjdk
MAINTAINER Sayantan Banerjee <sayantanb739@gmail.com>
ADD target/ReactCoronaDashboard-0.0.1-SNAPSHOT.jar ReactCoronaDashboard.jar
ENTRYPOINT ["java", "-jar", "/ReactCoronaDashboard.jar"]
EXPOSE 8700
