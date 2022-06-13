ARG VERSION=17
FROM openjdk:${VERSION}-jdk as BUILD
COPY  . /home/app/
WORKDIR /home/app/service
RUN ls
RUN chmod 777 -R /home/app/
RUN ../gradlew tasks
RUN  ../gradlew clean test bootJar

FROM openjdk:${VERSION}
EXPOSE 9095
RUN mkdir /app
RUN mkdir /app/service/liquibase/
COPY --from=build /home/app/service/build/libs/*.jar /app/service.jar
ENTRYPOINT ["java", "-jar","/app/service.jar"]