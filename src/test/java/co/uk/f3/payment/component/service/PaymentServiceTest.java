package co.uk.f3.payment.component.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
	public void saveOrUpdatePayment_shouldReturnNewlyCreatedPayment() throws Exception {

		Payment payment = PaymentCollectionGenerator.createBasicTestPayment();
		Optional<Payment> savedPayment = underTest.saveOrUpdatePayment(payment);
		assertNotNull(savedPayment.get());
		assertNotNull(savedPayment.get().getId());
	}

	@Test
	public void fetchPaymentById_shouldReturnAlreadyPersitedPayment() throws Exception {
		String id = UUID.randomUUID().toString();
		Payment payment = PaymentCollectionGenerator.createBasicTestPaymentWithId(id);
		mongoTemplate.insert(payment);

		Optional<Payment> savedPayment = underTest.fetchPaymentById(id);

		assertNotNull(savedPayment.get().getId());
		assertNotNull(savedPayment.get().getId().toString(), id);

	}

	@Test
	public void updatePayment_shouldUpdateAndReturnExistingPayment() throws Exception {
		String id = UUID.randomUUID().toString();
		Payment payment = PaymentCollectionGenerator.createBasicTestPaymentWithId(id);
		mongoTemplate.insert(payment);

		Optional<Payment> existingPayment = Optional.of(mongoTemplate.findById(id, Payment.class));
		assertNotNull(existingPayment.get().getId());
		assertEquals(existingPayment.get().getVersion(), 0);

		String organisationId = UUID.randomUUID().toString();
		existingPayment.get().setOrganisationId(organisationId);

		Optional<Payment> updatedPayment = underTest.saveOrUpdatePayment(existingPayment.get());

		assertNotNull(updatedPayment.get().getId());
		assertEquals(updatedPayment.get().getId().toString(), id);
		assertEquals(updatedPayment.get().getOrganisationId().toString(), organisationId);
		assertEquals(updatedPayment.get().getVersion(), 1);
	}

	@Test
	public void fetchPayments_shouldReturnAllAlreadyCreatedPaymemts() throws Exception {

		Payment p1 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment p2 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment p3 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment p4 = PaymentCollectionGenerator.createBasicTestPayment();

		mongoTemplate.insert(new ArrayList<>(Arrays.asList(p1, p2, p3, p4)), Payment.class);

		List<Payment> payments = underTest.fetchPayments().stream().collect(Collectors.toList());

		assertNotNull(payments);
		assertEquals(payments.size(), 4);
	}

	@Test
	public void deletePayment_shouldDeletePaymentFromDatabaseAndReturnNothing() {
		String id = UUID.randomUUID().toString();
		Payment payment = PaymentCollectionGenerator.createBasicTestPaymentWithId(id);
		mongoTemplate.insert(payment);

		Payment existingPayment = mongoTemplate.findById(id, Payment.class);
		String existingId = existingPayment.getId().toString();

		assertNotNull(existingId);
		assertEquals(existingPayment.getVersion(), 0);

		underTest.deletePaymentById(existingId);

		Payment deletedPayment = mongoTemplate.findById(existingId, Payment.class);
		assertEquals(deletedPayment, null);

	}
	

	@After
	public void tearDown() {
		mongoTemplate.dropCollection(Payment.class);
	}

}
