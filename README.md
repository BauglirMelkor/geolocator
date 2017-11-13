# geolocator
 Store Locator Mongo DB <br />
 Spring Boot Application <br />
 Application uses embedded MongoDB <br />
 Store objects in the stores.json file parsed and then inserted in the database during the application starts <br />
 Takes 4 inputs latitude,longitude,distance and result size <br />
 Returns closest stores regarding to specified inputs <br />
 Basic angularjs client implemented in order to call the rest api <br />
 
 You may run locator-0.0.1-SNAPSHOT.jar under any folder that you copy it.  <br />

1. Run the command below  <br />

java -jar locator-0.0.1-SNAPSHOT.jar  <br />

2. Open a web browser and copy the link below  <br />

http://localhost:8080/index.html  <br />

3. You may enter the latitude, longitude, distance and result size information  <br />

4. Distance helps you to find closest stores in a given distance. If there is no store around the distance you specify then the result will be empty.  <br />

5. Default size of the result size is 5 but you may change it regarding to your needs.  <br />

