FROM openjdk:11
EXPOSE 8082
ARG JAR_FILE=target/application-1.1.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]