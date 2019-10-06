FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8084
COPY  ./target/NotificationService.jar /home/NotificationService.jar
ENTRYPOINT ["java", "-jar", "/home/NotificationService.jar"]