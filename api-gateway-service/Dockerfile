FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
COPY  ./target/APIGatewayService.jar /home/APIGatewayService.jar
ENTRYPOINT ["java", "-jar", "/home/APIGatewayService.jar"]