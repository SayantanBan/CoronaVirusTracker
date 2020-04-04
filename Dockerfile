FROM openjdk:11
MAINTAINER Sayantan Banerjee <sayantanb739@gmail.com>
# Add the application's jar to the container
ADD target/ReactCoronaDashboard-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8700
