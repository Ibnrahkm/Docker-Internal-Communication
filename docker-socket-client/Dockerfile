# build stage
FROM gradle:jdk17-alpine AS BUILD_STAGE
WORKDIR /build_stage

COPY . .

RUN gradle clean build -x test

# package stage

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

ARG version=1.0.0
ARG JAR_NAME=demo-0.0.1-SNAPSHOT

COPY --from=BUILD_STAGE /build_stage/build/libs/${JAR_NAME}.jar clientapp.jar

ENTRYPOINT [ "java","-jar","clientapp.jar" ]
