# form3-payment
This Service provides the following operations:
1) Create new Payment:
2) Fetch Payment by Id
3) Update Existing Payment and 
4) Delete existing payment
5) List all existing paymens

## Test Execution Steps
1) Make sure :
  i) docker and docker compose are installed
  ii) stop all service running on port 27017
2) checkout master
3) Execute Tests
   i) gradle clean tests: for unit and component tests
   ii) gradle clean cucumber: for cucumber (end to end tests)
   
Note: For tests execution there is no need to manually start docker composition located in the test package (src/test/resources) since this is taken care of automatically by gradle.

## Running the service
1) Make sure port 27017 is free
2) Start Mongo DB by navigating to "Your local workspace"/f3-payment/src/test/resources and executing "docker-compose up"
3) Run gradle bootRun to start application
4) Browse to http://localhost:8080/swagger-ui.html#/ to list available operations or use other rest clients such as postman to mess around.

