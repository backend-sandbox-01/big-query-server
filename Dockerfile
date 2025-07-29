FROM openjdk:17-slim-bullseye AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

FROM openjdk:17-slim-bullseye
WORKDIR /usr/app
COPY --from=build /app/build/libs/*.jar /usr/app/server.jar
CMD ["java", "-jar", "/usr/app/server.jar"]
