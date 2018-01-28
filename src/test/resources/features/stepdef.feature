Feature: Server reply Hello World!
  
  Scenario: Client can retreive already created payment
    Given a client with organisationId <e83b4bea-7171-483f-9925-b8990c54a764> has made a payment
    When the client makes a request to the end point /payments/v1/ with its organisationId
    Then the response status is 200
    And the response body must contain ordganisation_id <e83b4bea-7171-483f-9925-b8990c54a764>
    And the payment made by the client.