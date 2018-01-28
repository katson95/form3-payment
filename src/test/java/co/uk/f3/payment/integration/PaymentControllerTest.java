package co.uk.f3.payment.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
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

import com.palantir.docker.compose.DockerComposeRule;

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

	@ClassRule
	public static DockerComposeRule docker = DockerComposeRule.builder().file("src/test/resources/docker-compose.yml")
			.build();

	@Before
	public void startUp() {
		mongoTemplate.dropCollection(Payment.class);
	}

	@Test
	public void createPayment_shouldReturnNewPayment() throws Exception {

		Payment p = PaymentCollectionGenerator.createBasicTestPayment();

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
	public void fetchPaymentByPaymentId_shouldReturnPaymentByPaymentId() throws Exception {

		String paymentId = UUID.randomUUID().toString();

		Payment p = PaymentCollectionGenerator.createBasicTestPaymentWithPaymentId(paymentId);

		mongoTemplate.insert(p);

		Payment payment = mongoTemplate.findAll(Payment.class).get(0);

		ResponseEntity<Payment> paymentDocument = restTemplate.getForEntity("/payments/v1/" + paymentId,
				Payment.class);

		assertEquals(payment.getPaymentId(), paymentDocument.getBody().getPaymentId());
	}

	@Test
	public void updatePayment_shouldUpdateAndReturnSelectedPayment() throws Exception {
		String paymentId1 = UUID.randomUUID().toString();

		Payment p = PaymentCollectionGenerator.createBasicTestPaymentWithPaymentId(paymentId1);

		mongoTemplate.insert(p);

		Payment toBeUpdatedPayment = mongoTemplate.findAll(Payment.class).get(0);

		String paymentId2 = UUID.randomUUID().toString();

		String paymentId = toBeUpdatedPayment.getPaymentId().toString();

		toBeUpdatedPayment.setPaymentId(paymentId2);

		restTemplate.put("/payments/v1/" + paymentId, toBeUpdatedPayment, Payment.class);

		Payment updatedDocument = mongoTemplate.findAll(Payment.class).get(0);
				
		assertNotNull(updatedDocument);
		assertEquals(updatedDocument.getPaymentId(), paymentId2);
		assertNotEquals(updatedDocument.getPaymentId(), paymentId1);
	}

	@Test
	public void deletePaymentByPaymentId_shouldDeletePaymentSelectedPayment() throws Exception {

		Payment payment = PaymentCollectionGenerator.createBasicTestPayment();

		mongoTemplate.insert(payment);
		
		Payment existingPayment = mongoTemplate.findAll(Payment.class).get(0);
		String paymentId = existingPayment.getId().toString();

		restTemplate.delete("/payments/v1/" + paymentId);

		assertEquals(mongoTemplate.findAll(Payment.class), Collections.emptyList());

	}

	@After
	public void tearDown() {
		mongoTemplate.dropCollection(Payment.class);
	}
}
