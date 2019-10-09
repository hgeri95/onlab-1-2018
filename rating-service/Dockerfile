FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8085
COPY  ./target/RatingService.jar /home/RatingService.jar
ENTRYPOINT ["java", "-jar", "/home/RatingService.jar"]