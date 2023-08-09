FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN maven clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/r-and-l-love-0.0.1-SNAPSHOT.jar r-and-l-love.jar
EXPOSE 8080
ENTRYPOINT ["java", "jar", "r-and-l-love.jar"]