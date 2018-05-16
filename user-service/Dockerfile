FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8081
COPY  ./target/UserService.jar /home/UserService.jar
ENTRYPOINT ["java", "-jar", "/home/UserService.jar"]