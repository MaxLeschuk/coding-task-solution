FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=target/admin-panel-1.1.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]