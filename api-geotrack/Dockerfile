FROM maven:3.9.7-amazoncorretto-21 AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine-full

COPY --from=build /app/target/api-geotrack-0.0.1-SNAPSHOT.jar /app/api-geotrack.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "api-geotrack.jar"]
