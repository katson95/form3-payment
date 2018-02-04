package co.uk.f3.payment.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.json.JSONTokener;
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
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.uk.f3.payment.exception.handler.DocumentNotFoundException;
import co.uk.f3.payment.model.domain.Payment;
import co.uk.f3.payment.unit.service.PaymentServiceTest;
import co.uk.f3.utils.PaymentCollectionGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentControllerTest {

	public static final Logger LOGGER = LoggerFactory.getLogger(PaymentControllerTest.class);

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Before
	public void startUp() {
		mongoTemplate.dropCollection(Payment.class);
	}

	@Test
	public void savePayment_shouldReturnNewPaymentResponseEntity() throws Exception {
		JSONObject paymentInput = new JSONObject(
				new JSONTokener(PaymentServiceTest.class.getResourceAsStream("/validation/data/payment.json")));

		ObjectMapper mapper = new ObjectMapper();
		Payment payment = mapper.readValue(paymentInput.toString(), Payment.class);
		
		LOGGER.info(payment.toString());
		
		

//		ResponseEntity<Payment> entity = restTemplate.postForEntity("/payments/v1/", p, Payment.class);
//		Payment payment = entity.getBody();
//		assertEquals(HttpStatus.CREATED, entity.getStatusCode());
//		assertNotNull(payment.getId());
//		assertEquals(entity.getStatusCodeValue(), 201);

	}
	
	@Test
	public void createPayment_shouldReturnNewPaymentResponseEntity() throws Exception {

		Payment p = PaymentCollectionGenerator.createBasicTestPayment();

		ResponseEntity<Payment> document = restTemplate.postForEntity("/payments/v1/", p, Payment.class);
		assertEquals(HttpStatus.CREATED, document.getStatusCode());
		assertNotNull(document.getBody().getId());
		assertEquals(document.getStatusCodeValue(), 201);

	}

	@Test
	public void fetchPayments_shouldReturnAllPaymentResponseEntities() throws Exception {

		Payment p1 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment p2 = PaymentCollectionGenerator.createBasicTestPayment();

		mongoTemplate.insert(new ArrayList<>(Arrays.asList(p1, p2)), Payment.class);

		ResponseEntity<Set<Resource<Payment>>> responseEntities = restTemplate.exchange("/payments/v1/", HttpMethod.GET,
				null, new ParameterizedTypeReference<Set<Resource<Payment>>>() {
				}, Collections.emptyMap());
		List<Resource<Payment>> payments = responseEntities.getBody().stream().collect(Collectors.toList());
		assertEquals(responseEntities.getStatusCode(), HttpStatus.OK);
		assertEquals(responseEntities.getBody().size(), 2);
		assertEquals(responseEntities.getStatusCodeValue(), 200);
		assertEquals(CollectionUtils.isEmpty(payments), false);
	}

	@Test
	public void fetchPaymentById_shouldReturnExistingPaymentResponseEntity() throws Exception {

		String id = UUID.randomUUID().toString();
		String organisationId = UUID.randomUUID().toString();
		
		Payment p = PaymentCollectionGenerator.createBasicTestPaymentWithOrganisationId(id, organisationId);

		mongoTemplate.insert(p);

		ResponseEntity<Payment> paymentDocument = restTemplate.getForEntity("/payments/v1/" + id, Payment.class);

		assertNotNull(paymentDocument.getBody().getId());
		assertEquals(paymentDocument.getBody().getOrganisationId(), organisationId);
	}

	@Test
	public void updatePayment_shouldUpdateAndSelectedPayment() throws Exception {
		String id = UUID.randomUUID().toString();
		Payment p = PaymentCollectionGenerator.createBasicTestPaymentWithId(id);

		mongoTemplate.insert(p);

		Payment toBeUpdatedPayment = mongoTemplate.findById(id, Payment.class);

		String organisationId = UUID.randomUUID().toString();

		toBeUpdatedPayment.setOrganisationId(organisationId);

		restTemplate.put("/payments/v1/" + id, toBeUpdatedPayment, Payment.class);

		Payment updatedDocument = mongoTemplate.findById(id, Payment.class);
		assertNotNull(updatedDocument);
		assertEquals(updatedDocument.getId(), id);
		assertEquals(updatedDocument.getOrganisationId(), organisationId);
	}

	@Test
	public void deletePaymentById_shouldDeleteSelectedPayment() throws Exception {
		String id = UUID.randomUUID().toString();
		Payment payment = PaymentCollectionGenerator.createBasicTestPaymentWithId(id);

		mongoTemplate.insert(payment);

		Payment toBeDeletedPayment = mongoTemplate.findById(id, Payment.class);
		assertNotNull(toBeDeletedPayment.getId());

		restTemplate.delete("/payments/v1/" + toBeDeletedPayment.getId());

		Payment deletedPayment = mongoTemplate.findById(id, Payment.class);
		assertEquals(deletedPayment, null);

	}

	@Test
	public void findPaymentById_ShouldThrowEntityNotFoundExceptionForUnknownPayment() throws DocumentNotFoundException {
		String id = UUID.randomUUID().toString();
		Payment payment = PaymentCollectionGenerator.createBasicTestPaymentWithId(id);
		mongoTemplate.insert(payment);
		
		Payment toBeDeletedPayment = mongoTemplate.findById(id, Payment.class);

		mongoTemplate.remove(toBeDeletedPayment);

		restTemplate.getForEntity("/payments/v1/" + id, Payment.class);

	}

	@After
	public void tearDown() {
		mongoTemplate.dropCollection(Payment.class);
	}
}
