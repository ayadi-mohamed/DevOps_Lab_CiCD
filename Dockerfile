


FROM adoptopenjdk/openjdk11:alpine-jre as runtime

RUN mkdir -p /home/project


WORKDIR /home/project
COPY /var/jenkins_home/.m2/repository/com/example/devops-project/0.0.1-SNAPSHOT/devops-project-0.0.1-SNAPSHOT.jar /home/project/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/project/app.jar"]
