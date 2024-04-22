# Build stage
FROM gradle:jdk17-alpine AS BUILD_STAGE
WORKDIR /build_stage
COPY . .
RUN gradle clean build -x test



# Package stage
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

ARG version=1.0.0
ARG JAR_NAME=demo-0.0.1-SNAPSHOT
ARG SERVER_PORT=8080

# COPY ./build/libs/${JAR_NAME}.jar serverapp.jar
# we can use ${pwd} here as well. here we just copied the jar file directly and run it using java jar command using entrypoint
# now we'll copy entire codes into it and will build it from there and then will run

COPY --from=BUILD_STAGE /build_stage/build/libs/${JAR_NAME}.jar serverapp.jar

# ENTRYPOINT [ "java","-jar","serverapp.jar" ]
# we were running directly the jar from app directory as we copied only the jar file

# now we are running jar from build folder as we copied the code and built into the image directly
ENTRYPOINT [ "java","-jar","serverapp.jar" ]

