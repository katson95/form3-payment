package co.uk.f3.payment.component.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.palantir.docker.compose.DockerComposeRule;

import co.uk.f3.manager.PaymentCollectionGenerator;
import co.uk.f3.payment.model.domain.Payment;
import co.uk.f3.payment.service.IPaymentService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentServiceTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private IPaymentService underTest;

	@ClassRule
	public static DockerComposeRule docker = DockerComposeRule.builder().file("src/test/resources/docker-compose.yml")
			.build();

	/**
	 * Perform resource (e.g. database) initialisation operations before each test
	 * is executed.
	 */
	@Before
	public void startUp() {
		mongoTemplate.dropCollection(Payment.class);
	}

	/**
	 * Tests Payment creation method.
	 * 
	 * @throws Exception
	 */
	@Test
	public void createPayment_shouldReturnNewPayment() throws Exception {

		Payment payment = PaymentCollectionGenerator.createBasicTestPayment();
		Optional<Payment> savedPayment = underTest.saveOrUpdatePayment(payment);
		assertNotNull(savedPayment.get());
		assertNotNull(savedPayment.get().getId());
	}

	/**
	 * Tests payment retrieval by paymentId Operation.
	 * 
	 * @throws Exception
	 */
	@Test
	public void fetchPaymentBypaymentId_shouldReturnAlreadyPersitedPayment() throws Exception {
		String paymentId = UUID.randomUUID().toString();
		Payment payment = PaymentCollectionGenerator.createBasicTestPaymentWithPaymentId(paymentId);
		mongoTemplate.insert(payment);

		Optional<Payment> savedPayment = underTest.fetchPaymentById(paymentId);

		assertNotNull(savedPayment.get().getId());
		assertNotNull(savedPayment.get().getOrganisationId(), paymentId);

	}

	@Test
	public void updatePayment_shouldUpdateAndReturnExistingPayment() throws Exception {
		String paymentId1 = UUID.randomUUID().toString();
		Payment payment = PaymentCollectionGenerator.createBasicTestPaymentWithPaymentId(paymentId1);
		mongoTemplate.insert(payment);

		Payment existingPayment = mongoTemplate.findAll(Payment.class).get(0);
		assertNotNull(existingPayment.getId());
		assertEquals(existingPayment.getOrganisationId(), paymentId1);
		assertEquals(existingPayment.getVersion(), 0);

		String paymentId2 = UUID.randomUUID().toString();
		existingPayment.setOrganisationId(paymentId2);

		Optional<Payment> updatedPayment = underTest.saveOrUpdatePayment(existingPayment);
		assertNotNull(updatedPayment.get().getId());
		assertEquals(updatedPayment.get().getOrganisationId(), paymentId2);
		assertEquals(updatedPayment.get().getVersion(), 1);
	}

	@Test
	public void fetchPayments_shouldReturnAllAlreadyCreatedPaymemts() throws Exception {

		Payment p1 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment p2 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment p3 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment p4 = PaymentCollectionGenerator.createBasicTestPayment();

		mongoTemplate.insert(new ArrayList<>(Arrays.asList(p1, p2, p3, p4)), Payment.class);

		Set<Payment> payments = underTest.fetchPayments().stream().collect(Collectors.toSet());

		assertNotNull(payments);
		assertEquals(payments.size(), 4);
	}

	@Test
	public void deletePayment_shouldDeletePaymentFromDatabaseAndReturnNothing() {
		Payment payment = PaymentCollectionGenerator.createBasicTestPayment();
		mongoTemplate.insert(payment);

		Payment existingPayment = mongoTemplate.findAll(Payment.class).get(0);
		String paymentId = existingPayment.getId().toString();

		assertNotNull(paymentId);
		assertEquals(existingPayment.getVersion(), 0);

		underTest.deletePaymentByPaymentId(paymentId);

		Payment deletedPayment = mongoTemplate.findById(paymentId, Payment.class);
		assertEquals(deletedPayment, null);

	}

	@After
	public void tearDown() {
		mongoTemplate.dropCollection(Payment.class);
	}

}
