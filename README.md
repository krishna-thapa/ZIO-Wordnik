# Simple API client

## Technologies used:
1. [ZIO](https://zio.dev/)
2. [Http4s](https://http4s.org/v0.21/client/)
3. [Circe](https://circe.github.io/circe/)

## References
1. [ZIO + Http4s: a simple API client](https://juliano-alves.com/2020/04/20/zio-http4s-a-simple-api-client/)
2. [ZIO, Http4s, Auth, Codecs and zio-test](https://timpigden.github.io/_pages/zio-http4s/intro.html)
3. [From idea to product with ZLayer](https://scala.monster/welcome-zio/)

## About the Client API
- [Wordnik API](https://developer.wordnik.com/)
- Have to open the account and get the api token to use the endpoints

## About the project
- As a future reference to be used with [Inspirational-quote-api project](https://github.com/krishna-thapa/Inspirational-quote-api)
- Get the word of the day with pronunciation and audio details and send to font-end of [Inspirational-quote-vue](https://github.com/krishna-thapa/Inspirational-quote-vue)

## Reason behind
- For a personal project
- To learn functional and concurrent programming using ZIO

## To run the project
```
sbt clean compile test run
```

**Make sure that you have your own API token if you want to use Wordnik endpoints**

## Future improvements
- Write some test on client API to check the response code or error message