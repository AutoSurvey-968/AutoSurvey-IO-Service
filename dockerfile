FROM openjdk:8-jdk-alpine
WORKDIR /app

COPY target/io-service.jar .

EXPOSE 8083

CMD [ "java", "-jar", "io-service.jar" ]