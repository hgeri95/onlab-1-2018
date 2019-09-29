FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 80
COPY  ./target/APIGatewayService.jar /home/APIGatewayService.jar
ENTRYPOINT ["java", "-jar", "/home/APIGatewayService.jar"]