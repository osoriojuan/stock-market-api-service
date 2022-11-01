FROM openjdk:17-alpine
MAINTAINER "Juan Osorio"
COPY ./build/libs/stock-market-api-service-1.0.0.jar stock-market-api-service-1.0.0.jar
# EXPOSE 8080
CMD ["java","-Dserver.port=$PORT","-Xmx300m","-Xss512k","-XX:CICompilerCount=2","-Dfile.encoding=UTF-8","-XX:+UseContainerSupport","-jar","/stock-market-api-service-1.0.0.jar"]
