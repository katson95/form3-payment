package co.uk.f3.payment.e2e;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import co.uk.f3.manager.PaymentCollectionGenerator;
import co.uk.f3.payment.model.domain.Payment;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class PaymentStepDefinition {

	public static final Logger LOGGER = LoggerFactory.getLogger(PaymentStepDefinition.class);

	@Autowired
	private TestRestTemplate restTemplate;

	private ResponseEntity<Payment> response;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	

	@Given("^a client with organisationId (.*) has made a payment$")
	public void iCallGetOn(String paymentId) throws Throwable {
		Payment p = PaymentCollectionGenerator.createBasicTestPaymentWithPaymentId(paymentId);
		mongoTemplate.insert(p);

		this.response = this.restTemplate.getForEntity("/payments/v1/" + paymentId, Payment.class);

	}

	@When("^the client makes a request to the end point /payments/v1/ with its organisationId$")
	public void the_client_class_the_end_point_payments_with_its_organisationId() throws Throwable {
		Payment createdPayment = this.response.getBody();
		assertNotNull(createdPayment.getPaymentId());
	}

	@Then("^the response status is (\\d+)$")
	public void theResponseStatusIs(int status) throws Throwable {
		Assert.assertEquals(status, response.getStatusCode().value());
	}

	@And("^the response body must contain ordganisation_id (.*)")
	public void theResponseBodyMustContainFieldWithValue(String paymentId) throws Throwable {
		Payment createdPayment = this.response.getBody();
		assertEquals(createdPayment.getPaymentId(), paymentId);
	}
	
	@And("^the payment made by the client")
	public void theResponseBodyMustContainPaymentMadeByTheClient() throws Throwable {
		assertNotNull(response.getBody());
	}
	
}
