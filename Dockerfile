FROM openjdk:17-alpine
ARG port
MAINTAINER "Juan Osorio"
COPY ./build/libs/stock-market-api-service-1.0.0.jar stock-market-api-service-1.0.0.jar
ENV PORT=$port
EXPOSE $PORT
ENTRYPOINT ["java","-jar","/stock-market-api-service-1.0.0.jar"]
