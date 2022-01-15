FROM adoptopenjdk/openjdk11
EXPOSE 8080
COPY target/JWT-0.0.1-SNAPSHOT.jar JWT.jar
ENTRYPOINT ["java","-jar","/JWT.jar"]