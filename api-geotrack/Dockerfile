# image maven to buil the project
FROM maven:3.9.7-amazoncorretto-21 AS build
# copy the folders to the image
COPY src /app/src
COPY pom.xml /app
# set a workspace(work folder)
WORKDIR /app
# comand maven to clean package, after compile again and DskipTests = skip tests
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine-full
# SO image

# copy jar archive to image folder
COPY --from=build /app/target/api-geotrack-0.0.1-SNAPSHOT.jar /app/api-geotrack.jar

WORKDIR /app
# expose port
EXPOSE 8080
# comand for started container
CMD ["java", "-jar", "api-geotrack.jar"]
