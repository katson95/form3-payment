package co.uk.f3.payment.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import co.uk.f3.manager.PaymentCollectionGenerator;
import co.uk.f3.payment.model.domain.Payment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentControllerTest {

	public static final Logger LOGGER = LoggerFactory.getLogger(PaymentControllerTest.class);

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MongoTemplate mongoTemplate;

	// @ClassRule
	// public static DockerComposeRule docker =
	// DockerComposeRule.builder().file("src/test/resources/docker-compose.yml")
	// .build();

	@Before
	public void startUp() {
		mongoTemplate.dropCollection(Payment.class);
	}

	@Test
	public void createPayment_shouldReturnNewPayment() throws Exception {

		Payment p = PaymentCollectionGenerator.createBasicTestPaymentWithoutId();

		ResponseEntity<Payment> entity = restTemplate.postForEntity("/payments/v1/", p, Payment.class);
		Payment payment = entity.getBody();
		assertEquals(HttpStatus.CREATED, entity.getStatusCode());
		assertNotNull(payment.getId());

	}

	@Test
	public void fetchPayments_shouldReturnAllPayments() throws Exception {

		Payment p1 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment p2 = PaymentCollectionGenerator.createBasicTestPayment();

		mongoTemplate.insert(new ArrayList<>(Arrays.asList(p1, p2)), Payment.class);

		ResponseEntity<Set<Resource<Payment>>> responseDocument = restTemplate.exchange("/payments/v1/", HttpMethod.GET,
				null, new ParameterizedTypeReference<Set<Resource<Payment>>>() {
				}, Collections.emptyMap());

		assertEquals(responseDocument.getStatusCode(), HttpStatus.OK);
		assertEquals(2, responseDocument.getBody().size());
	}

	@Test
	public void fetchPaymentById_shouldReturnPaymentBydocumentId() throws Exception {

		String organisationId = UUID.randomUUID().toString();

		Payment p = PaymentCollectionGenerator.createBasicTestPaymentWithOrganisationId(organisationId);

		mongoTemplate.insert(p);

		Payment payment = mongoTemplate.findAll(Payment.class).get(0);
		String paymentId = payment.getId().toString();

		ResponseEntity<Payment> paymentDocument = restTemplate.getForEntity("/payments/v1/" + paymentId, Payment.class);

		assertNotNull(payment.getId().toString());
		assertEquals(payment.getOrganisationId(), paymentDocument.getBody().getOrganisationId());
	}

	@Test
	public void updatePayment_shouldUpdateAndReturnSelectedPayment() throws Exception {

		Payment p = PaymentCollectionGenerator.createBasicTestPayment();

		mongoTemplate.insert(p);

		Payment toBeUpdatedPayment = mongoTemplate.findAll(Payment.class).get(0);

		String organisationId = UUID.randomUUID().toString();

		String paymentId = toBeUpdatedPayment.getId().toString();

		toBeUpdatedPayment.setOrganisationId(organisationId);

		restTemplate.put("/payments/v1/" + paymentId, toBeUpdatedPayment, Payment.class);

		Payment updatedDocument = mongoTemplate.findAll(Payment.class).get(0);

		assertNotNull(updatedDocument);
		assertEquals(updatedDocument.getId().toString(), paymentId);
		assertEquals(updatedDocument.getOrganisationId(), organisationId);
	}

	@Test
	public void deletePaymentBydocumentId_shouldDeletePaymentSelectedPayment() throws Exception {

		Payment payment = PaymentCollectionGenerator.createBasicTestPayment();

		mongoTemplate.insert(payment);

		Payment existingPayment = mongoTemplate.findAll(Payment.class).get(0);
		String documentId = existingPayment.getId().toString();

		restTemplate.delete("/payments/v1/" + documentId);

		assertEquals(mongoTemplate.findAll(Payment.class), Collections.emptyList());

	}

	@After
	public void tearDown() {
		mongoTemplate.dropCollection(Payment.class);
	}
}
