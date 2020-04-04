FROM openjdk:11
FROM openjdk
MAINTAINER Sayantan Banerjee <sayantanb739@gmail.com>
ADD target/ReactCoronaDashboard.jar ReactCoronaDashboard.jar
ENTRYPOINT ["java", "-jar", "/ReactCoronaDashboard.jar"]
EXPOSE 8700
