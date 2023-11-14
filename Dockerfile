


FROM adoptopenjdk/openjdk11:alpine-jre as runtime



COPY ./target/*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
