FROM openjdk:11
FROM openjdk
MAINTAINER Sayantan Banerjee <sayantanb739@gmail.com>
# Add a volume pointing to /tmp
VOLUME /tmp
# The application's jar file
ARG JAR_FILE=target/ReactCoronaDashboard-0.0.1-SNAPSHOT.jar
# Add the application's jar to the container
ADD ${JAR_FILE} ReactCoronaDashboard.jar
ENTRYPOINT ["java", "-jar", "/ReactCoronaDashboard.jar"]
EXPOSE 8700
