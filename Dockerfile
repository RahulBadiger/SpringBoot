#FROM openjdk:11
#COPY target/*.jar datasetproj.jar
#EXPOSE 8090
#ENTRYPOINT ["java","-jar","datasetproj.jar"]



FROM openjdk:11
EXPOSE 8090
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]