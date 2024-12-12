FROM eclipse-temurin:21-jre

RUN mkdir -p /opt/application
COPY --chmod=765 *-runner.jar /opt/application/backend.jar
WORKDIR /opt/application
CMD [ "java", "-jar", "backend.jar" ]
