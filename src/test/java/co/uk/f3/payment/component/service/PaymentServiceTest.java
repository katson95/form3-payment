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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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


	@Before
	public void startUp() {
		mongoTemplate.dropCollection(Payment.class);
	}


	@Test
	public void createPayment_shouldReturnNewPayment() throws Exception {

		Payment payment = PaymentCollectionGenerator.createBasicTestPaymentWithoutId();
		Optional<Payment> savedPayment = underTest.saveOrUpdatePayment(payment);
		assertNotNull(savedPayment.get());
		assertNotNull(savedPayment.get().getId());
	}


	@Test
	public void fetchPaymentByPaymentId_shouldReturnAlreadyPersitedPayment() throws Exception {
		Payment payment = PaymentCollectionGenerator.createBasicTestPaymentWithoutId();
		mongoTemplate.insert(payment);

		String paymentId = mongoTemplate.findAll(Payment.class).get(0).getId().toString();
		Optional<Payment> savedPayment = underTest.fetchPaymentById(paymentId);

		assertNotNull(savedPayment.get().getId());
		assertNotNull(savedPayment.get().getId().toString(), paymentId);

	}

	@Test
	public void updatePayment_shouldUpdateAndReturnExistingPayment() throws Exception {
		Payment payment = PaymentCollectionGenerator.createBasicTestPaymentWithoutId();
		mongoTemplate.insert(payment);

		Payment existingPayment = mongoTemplate.findAll(Payment.class).get(0);
		assertNotNull(existingPayment.getId());
		assertEquals(existingPayment.getVersion(), 0);
		
		String organisationId = UUID.randomUUID().toString();
		String paymentId = existingPayment.getId().toString();
		existingPayment.setOrganisationId(organisationId);


		Optional<Payment> updatedPayment = underTest.saveOrUpdatePayment(existingPayment);
		
		assertNotNull(updatedPayment.get().getId());
		assertEquals(updatedPayment.get().getId().toString(), paymentId);
		assertEquals(updatedPayment.get().getVersion(), 1);
	}

	@Test
	public void fetchPayments_shouldReturnAllAlreadyCreatedPaymemts() throws Exception {

		Payment p1 = PaymentCollectionGenerator.createBasicTestPaymentWithoutId();
		Payment p2 = PaymentCollectionGenerator.createBasicTestPaymentWithoutId();
		Payment p3 = PaymentCollectionGenerator.createBasicTestPaymentWithoutId();
		Payment p4 = PaymentCollectionGenerator.createBasicTestPaymentWithoutId();

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

		underTest.deletePaymentById(paymentId);

		Payment deletedPayment = mongoTemplate.findById(paymentId, Payment.class);
		assertEquals(deletedPayment, null);

	}

	@After
	public void tearDown() {
		mongoTemplate.dropCollection(Payment.class);
	}

}
