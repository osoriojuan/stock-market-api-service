FROM openjdk:17-alpine
MAINTAINER "Juan Osorio"
COPY ./build/libs/stock-market-api-service-1.0.0.jar stock-market-api-service-1.0.0.jar
# EXPOSE 8080
CMD ["java","-jar","-Dserver.port=%PORT%","/stock-market-api-service-1.0.0.jar"]
