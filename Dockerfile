


FROM adoptopenjdk/openjdk11:alpine-jre as runtime

RUN mkdir -p /home/project


WORKDIR /home/project
COPY target/*.jar /home/project/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/project/app.jar"]
