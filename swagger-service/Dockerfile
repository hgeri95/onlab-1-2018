FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8083
COPY  ./target/SwaggerService.jar /home/SwaggerService.jar
ENTRYPOINT ["java", "-jar", "/home/SwaggerService.jar"]