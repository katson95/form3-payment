package co.uk.f3.payment.e2e;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.uk.f3.payment.model.domain.Payment;
import co.uk.f3.payment.unit.service.PaymentServiceTest;
import co.uk.f3.utils.PaymentCollectionGenerator;
import cucumber.api.java.After;
import cucumber.api.java.Before;
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

	private ObjectMapper mapper;

	private Payment payment;

	private JSONObject paymentInput;

	@Autowired
	private MongoTemplate mongoTemplate;

	public String paymentId;

	public PaymentStepDefinition(MongoTemplate mongoTemplate) {
		mapper = new ObjectMapper();
		mongoTemplate.dropCollection(Payment.class);
	}

	@Before
	public void beforeScenario() {
		mongoTemplate.dropCollection(Payment.class);
	}

	/****** CREATE PAYMENT ******/

	@Given("^payment with payment-information \"([^\"]*)\"$")
	public void payment_with_payment_information(String location) throws Throwable {
		this.paymentInput = new JSONObject(
				new JSONTokener(PaymentServiceTest.class.getResourceAsStream("/validation/data/" + location)));
		LOGGER.info("PAYMENT INFORMATION------------------>" + this.paymentInput.toString());
		this.mapper = new ObjectMapper();
		this.payment = mapper.readValue(paymentInput.toString(), Payment.class);
		LOGGER.info("PAYMENT INFORMATION------------------>" + payment.toString());
		assertNotNull(payment.getId());
	}

	@When("^i make a payment request to the endpoint \"([^\"]*)\"$")
	public void i_make_a_payment_request_to_the_endpoint_payments_v(String endpoint) throws Throwable {
		response = restTemplate.postForEntity("/payments/v1/", this.payment, Payment.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

	}

	@Then("^the payment service resturns response status (\\d+)$")
	public void the_payment_service_resturns_response_status(int statusCode) throws Throwable {
		assertEquals(response.getStatusCodeValue(), statusCode);
	}

	@Then("^the version number of the payment is (\\d+)$")
	public void the_version_number_of_the_payment_is(int version) throws Throwable {
		assertEquals(response.getBody().getVersion(), version);
	}

	@Then("^the response body must contain the initial payment submitted$")
	public void the_response_body_must_contain_the_initial_payment_submitted() throws Throwable {
		this.payment = mapper.readValue(this.paymentInput.toString(), Payment.class);
		assertEquals(this.payment, response.getBody());
	}

	/****** UPDATE PAYMENT ******/
	@Given("^an already existing payment \"([^\"]*)\" with id \"([^\"]*)\" from organisation with id \"([^\"]*)\"$")
	public void an_already_existing_payment_with_id_from_organisation_with_id(String location, String id,
			String organisationId) throws Throwable {
		this.payment = PaymentCollectionGenerator.createBasicTestPaymentWithOrganisationId(id, organisationId);
		mongoTemplate.insert(payment);
		this.payment = mongoTemplate.findById(payment.getId(), Payment.class);
		assertNotNull(payment.getId());
	}

	@When("^i make an update request to the endpoint \"([^\"]*)\" with organisationId \"([^\"]*)\"$")
	public void i_make_an_update_request_to_the_endpoint_with_organisationId(String endpoint, String organisationId)
			throws Throwable {
		this.payment.setOrganisationId(organisationId);
		restTemplate.put(endpoint + payment.getId(), payment, Payment.class);
		this.payment = mongoTemplate.findById(payment.getId(), Payment.class);
		assertNotNull(this.payment.getId());
	}

	@Then("^the version number of the payment is now (\\d+)$")
	public void the_version_number_of_the_payment_is_incremented_to(int version) throws Throwable {
		assertEquals(this.payment.getVersion(), version);
	}

	@When("^the organisationId  \"([^\"]*)\" of the updated payment is different from the initial value \"([^\"]*)\"$")
	public void the_organisationId_of_the_updated_payment_is_different_from_the_initial_value(String newOrdId,
			String oldOrgId) throws Throwable {
		assertNotEquals(oldOrgId, this.payment.getOrganisationId());
	}

	/****** FETCH PAYMENT ******/
	@Given("^an already existing payment \"([^\"]*)\" with id \"([^\"]*)\"$")
	public void an_already_existing_payment_with_id(String location, String id) throws Throwable {
		this.paymentInput = new JSONObject(
				new JSONTokener(PaymentServiceTest.class.getResourceAsStream("/validation/data/" + location)));
		this.mapper = new ObjectMapper();
		this.payment = mapper.readValue(paymentInput.toString(), Payment.class);
		mongoTemplate.insert(this.payment);
		assertNotNull(payment.getId());
	}

	@When("^i make a retrieval request to endpoint \"([^\"]*)\" with id \"([^\"]*)\"$")
	public void i_make_a_retrieval_request_to_endpoint_payments_v_with_id(String endpoint, String id) throws Throwable {
		this.response = restTemplate.getForEntity(endpoint + id, Payment.class);

		assertNotNull(this.response.getBody().getId());
	}

	@Then("^the payment service responds with status code (\\d+)$")
	public void the_payment_service_responds_with_status_code(int statusCode) throws Throwable {
		assertEquals(response.getStatusCodeValue(), statusCode);
	}

	/****** DELETE PAYMENT ******/
	@Given("^an exisiting payment \"([^\"]*)\" with id \"([^\"]*)\"$")
	public void an_exisiting_payment_with_id(String location, String id) throws Throwable {
		this.paymentInput = new JSONObject(
				new JSONTokener(PaymentServiceTest.class.getResourceAsStream("/validation/data/" + location)));
		this.mapper = new ObjectMapper();
		this.payment = mapper.readValue(paymentInput.toString(), Payment.class);
		mongoTemplate.insert(this.payment);
		assertNotNull(payment.getId());
	}

	@When("^i make a deletion request to the endpoint \"([^\"]*)\"$")
	public void i_make_a_deletion_request_to_the_endpoint_payments_v(String id) throws Throwable {
		restTemplate.delete("/payments/v1/" + id);
	}

	@And("^i make a retrieval request to the endpoint \"([^\"]*)\" with id \"([^\"]*)\"$")
	public void i_make_a_retrieval_request_to_the_endpoint_payments_v_with_id(String enpoint, String id)
			throws Throwable {
		response = restTemplate.getForEntity(enpoint + id, Payment.class);

		assertNotNull(response.getBody());
	}

	@Then("^the response should not contain payment \"([^\"]*)\"$")
	public void the_response_should_not_contain_payment(String arg1) throws Throwable {
		assertNotEquals(this.payment, this.response.getBody());
	}

	@After
	public void afterScenario() {
		mongoTemplate.dropCollection(Payment.class);
	}

}
