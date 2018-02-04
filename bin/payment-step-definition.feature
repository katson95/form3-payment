Feature: F3 Payment Service
    
 Scenario:	Creating new payment
 	Given payment with payment-information "payment.json"
 	When i make a payment request to the endpoint "/payments/v1/"
 	Then the payment service resturns response status 201
 	And the version number of the payment is 0
 	 And the response body must contain the initial payment submitted
 	
Scenario:	Updating already existing payment.
	Given an already existing payment "payment.json" with id "4ee3a8d8-ca7b-4290-a52c-dd5b6165ec43" from organisation with id "00000000-7171-483f-9925-b8990c54a764"
	When i make an update request to the endpoint "/payments/v1/" with organisationId "11111111-7171-483f-9925-b8990c54a764"
	And the version number of the payment is now 1
 	 And the organisationId  "11111111-7171-483f-9925-b8990c54a764" of the updated payment is different from the initial value "00000000-7171-483f-9925-b8990c54a764"

Scenario:	Retrieving already existing payment.
	Given an already existing payment "payment.json" with id "4ee3a8d8-ca7b-4290-a52c-dd5b6165ec43"
	When i make a retrieval request to endpoint "/payments/v1/" with id "4ee3a8d8-ca7b-4290-a52c-dd5b6165ec43"
	Then the payment service responds with status code 200
# 	 And the response body must contain the initial payment submitted
 	 
Scenario:	Deleting existing payment.
	Given an exisiting payment "payment.json" with id "4ee3a8d8-ca7b-4290-a52c-dd5b6165ec43"
	When i make a deletion request to the endpoint "/payments/v1/"
#	Then the payment service responds with status code 200
	And i make a retrieval request to the endpoint "/payments/v1/" with id "e83b4bea-7171-483f-9925-b8990c54a764"
	Then the response should not contain payment "payment.json"
	
	