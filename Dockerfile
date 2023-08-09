FROM openjdk:17.0.1-jdk-slim
COPY target/r-and-l-love-0.0.1-SNAPSHOT.jar r-and-l-love.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "r-and-l-love.jar"]