FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
COPY  ./target/APIGateway.jar /home/APIGateway.jar
ENTRYPOINT ["java", "-jar", "/home/APIGateway.jar"]