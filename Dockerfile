#FROM openjdk:11
#COPY target/*.jar datasetproj.jar
#EXPOSE 8090
#ENTRYPOINT ["java","-jar","datasetproj.jar"]



#FROM openjdk:11
#EXPOSE 8090
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]


FROM openjdk:11-jdk-slim
VOLUME /tmp
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
COPY target/datasetProj-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=container", "-jar", "/app/app.jar"]
