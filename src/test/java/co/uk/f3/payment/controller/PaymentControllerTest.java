package co.uk.f3.payment.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import co.uk.f3.manager.PaymentCollectionGenerator;
import co.uk.f3.payment.model.Payment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	/**
	 * Test for Create new Payment Operation.
	 * 
	 * @throws Exception
	 */
	@Test
	public void Test_Create_Payment() throws Exception {

		Payment p = PaymentCollectionGenerator.createBasicTestPayment();

		ResponseEntity<Payment> entity = restTemplate.postForEntity("/payments/", p, Payment.class);
		Payment payment = entity.getBody();
		assertEquals(HttpStatus.CREATED, entity.getStatusCode());
		assertNotNull(payment.getId());
		
	}

	/**
	 * Test for Get all Payments Operation.
	 * 
	 * @throws Exception
	 */
	@Test
	public void Test_Get_All_Payments() throws Exception {

		Payment p1 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment p2 = PaymentCollectionGenerator.createBasicTestPayment();

		restTemplate.postForEntity("/payments/", p1, Payment.class);
		restTemplate.postForEntity("/payments/", p2, Payment.class);

		ResponseEntity<Set<Resource<Payment>>> responseDocument = restTemplate.exchange("/agents/", HttpMethod.GET,
				null, new ParameterizedTypeReference<Set<Resource<Payment>>>() {
				}, Collections.emptyMap());

		assertEquals(responseDocument.getStatusCode(), HttpStatus.OK);
		assertEquals(2, responseDocument.getBody().size());
	}

	/**
	 * Test for Get Payment By ID Operation.
	 * 
	 * @throws Exception
	 */
	@Test
	public void Test_Get_Payment_By_OrganisationId() throws Exception {

		String organisation_id = UUID.randomUUID().toString();

		Payment p = PaymentCollectionGenerator.createBasicTestPaymentWithOrganisationId(organisation_id);

		ResponseEntity<Payment> payment = restTemplate.postForEntity("/payments/", p, Payment.class);

		ResponseEntity<Payment> paymentDocument = restTemplate.getForEntity("/payments/" + organisation_id,
				Payment.class);

		assertEquals(payment.getBody().getOrganisationId(), paymentDocument.getBody().getId());
	}

	/**
	 * Test for Update Payment Operation.
	 * 
	 * @throws Exception
	 */
	@Test
	public void Test_Update_Payment() throws Exception {
		String organisation_id1 = UUID.randomUUID().toString();

		Payment p = PaymentCollectionGenerator.createBasicTestPaymentWithOrganisationId(organisation_id1);

		ResponseEntity<Payment> paymentDocument = restTemplate.postForEntity("/payments/", p, Payment.class);

		Payment toBeUpdatedPayment = paymentDocument.getBody();

		String organisation_id2 = UUID.randomUUID().toString();

		toBeUpdatedPayment.setOrganisationId(organisation_id2);
		;
		restTemplate.put("/payments/", toBeUpdatedPayment, Payment.class);

		ResponseEntity<Payment> updatedDocument = restTemplate.getForEntity("/payments/" + organisation_id2,
				Payment.class);

		assertNotNull(updatedDocument.getBody());
		assertEquals(updatedDocument.getBody().getOrganisationId(), organisation_id2);
		assertNotEquals(updatedDocument.getBody().getOrganisationId(), organisation_id1);
	}

	/**
	 * Test for Delete Payment Operation
	 * 
	 * @throws Exception
	 */
	@Test
	public void Test_Delete_Payment() throws Exception {
		String organisation_id = UUID.randomUUID().toString();

		Payment p = PaymentCollectionGenerator.createBasicTestPaymentWithOrganisationId(organisation_id);

		ResponseEntity<Payment> payment = restTemplate.postForEntity("/payments/", p, Payment.class);
		assertNotNull(payment.getBody().getId());

		restTemplate.delete("/payments/" + organisation_id);

		ResponseEntity<Payment> paymentDocument = restTemplate.getForEntity("/payments/" + organisation_id,
				Payment.class);
		assertEquals(paymentDocument.getBody(), null);

	}
}
