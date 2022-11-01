# stock-market-api-service
* Application to obtain information about the stock market.
* The application is compiled with Java 17 and the dependency manager is gradle.
* The application is designed with hexagonal architecture to favor domain-driven design.
* The application is deployed on Heroku: https://stock-market-api-service.herokuapp.com

### SignUp ###
The endpoint `POST` `/api/auth/signup` is used to create user registration and to be able to invoke the endpoint to obtain the authorization token
```json
{
"email":"your.email@email.com",
"password":"admin123456",
"name":"name",
"lastName":"lastName"
}
```
If registration is successful, you should expect the following response:
`status 200`
```json
{
  "message": "User registered successfully!"
}
```

### SignIn ###

The `POST` `/api/auth/signin` endpoint is used to authenticate as a registered user in order to obtain an authorization token.
```json
{
  "email": "your.email@email.com",
  "password": "admin123456"
}
```

If authentication is successful, you should expect the following response:
`status 200`
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5b3VyLmVtYWlsQGVtYWlsLmNvbSIsImlzcyI6IlN0b3JtcGF0aCIsImV4cCI6MTY2NzMzNTIxMywiaWF0IjoxNjY3MjQ4ODEzfQ.SgIes24Z2_pwaPsB_mhkkgT38BbPFJJmt6NhvUDc0oll6zBxRyl4wtp7WRUB5ccvdDXFuoh0vJdlPb9c3wv9uw",
  "email": "your.email@email.com",
  "roles": [
    "ROLE_ADMIN"
  ]
}
```

### StockMarketInfo ###

With the endpoint `GET` `/api/stock-market/stock-info?stock-symbol={SYMBOL}&time-series={TIME_SERIES}` you can get the stock market information of a market value _SYMBOL_.
You can try some of the following stocks for _SYMBOL_
*IBM
*AAPL
*MSFT
*GOOGL
*AMZN

You must specify a _TIME_SERIES_. It will allow you to calculate market information on a daily, weekly or monthly basis.
Here the TIME_SERIES available
* TIME_SERIES_DAILY
* TIME_SERIES_WEEKLY
* TIME_SERIES_MONTHLY

You must also include an authorization of type bearer token with the token resulting from the `/api/auth/signin` service.

The expected answer is the following:
`status 200`
```json
{
  "openPrice": 138.06,
  "higherPrice": 122.15,
  "lowerPrice": 115.545,
  "variationPercentageTwoLastClosingPrices": -0.15883419
}
```

# Previous requirements
* You must have JDK 17 installed and configured.
* You must have docker installed.

# How to deploy locally
* Run the command $`.\gradlew build` to build the app with the gradle wrapper
* Run the command $`docker build --tag=stock-market-api-service:latest .` to build the image
* Run the command $`docker run stock-market-api-service` to run the docker container. This is configured to receive requests through port 8080.

# Final considerations
* The application does not have unit tests, I have concentrated on the most important points according to the specification of the challenge
* I included a postman collection with the available requests
* An implementation on an in-memory database H2 is being used for practical purposes
