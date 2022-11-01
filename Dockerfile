FROM openjdk:17-alpine
MAINTAINER "Juan Osorio"
COPY ./build/libs/stock-market-api-service-1.0.0.jar stock-market-api-service-1.0.0.jar
EXPOSE 5000
ENTRYPOINT ["java","-jar","/stock-market-api-service-1.0.0.jar"]
