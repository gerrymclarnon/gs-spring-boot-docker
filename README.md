#Journey Fuel Cost Calculator

I (heavily) based this on https://spring.io/guides/gs/spring-boot-docker/ as I wanted to create a Docker image for a Spring Boot app.

The app has some limitations...
* exact dates must be used
* not much in the way of helpful error messages, e.g. invalid fuel type.
* no real tests, I've been using use https://github.com/apickli/apickli for the last few years
* in the real world I would create a Swagger (OpenAPI) spec and validate against a JSON schema derived from the spec.

To run from a the docker image version...
   >docker image pull gerrymclarnon/gs-spring-boot-docker
   
   >docker run -p 8081:8081 gerrymclarnon/gs-spring-boot-docker
  
If you would like to run locally then clone the repo and...
   >cd complete
   
   >./mvnw package && java -jar target/gs-spring-boot-docker-0.1.0.jar

Once the Spring Boot app is up and running open a browser and go to http://localhost:8081/api/v1/journey/cost?date=09/06/2003&fuelType=Diesel&mpg=50&mileage=100

If you have any issues drop me a line gerry@mclarnonworld.com 
   
   
   

