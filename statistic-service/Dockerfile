FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8086
COPY  ./target/StatisticService.jar /home/StatisticService.jar
ENTRYPOINT ["java", "-jar", "/home/StatisticService.jar"]