FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8082
COPY  ./target/CateringUnitService.jar /home/CateringUnitService.jar
ENTRYPOINT ["java", "-jar", "/home/CateringUnitService.jar"]