Feature: Server reply Hello World!
  
  Scenario: Client can retreive already created payment
    Given an organisation with id "e83b4bea-7171-483f-9925-b8990c54a764" has made a payment
    When the organisation makes a request to the end point /payments/v1/ with its id
    Then the response status is 200
    And the response body must contain id "e83b4bea-7171-483f-9925-b8990c54a764"
    And the payment made by the organisation