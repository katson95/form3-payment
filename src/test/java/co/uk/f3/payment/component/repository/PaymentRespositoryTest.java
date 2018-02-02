package co.uk.f3.payment.component.repository;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

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
import co.uk.f3.payment.repository.IPaymentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentRespositoryTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private IPaymentRepository underTest;

	@Before
	public void startUp() {
		mongoTemplate.dropCollection(Payment.class);
	}

	@Test
	public void fetchPaymentById_shouldReturnExistingPayment() throws Exception {
		
		Payment p1 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment p2 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment p3 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment p4 = PaymentCollectionGenerator.createBasicTestPayment();

		mongoTemplate.insert(new ArrayList<>(Arrays.asList(p1, p2, p3, p4)), Payment.class);
		
		String paymentId = mongoTemplate.findAll(Payment.class).get(0).getId().toString();

		Optional<Payment> savedPayment = underTest.findById(paymentId);

		assertNotNull(savedPayment.get().getId());
		assertNotNull(savedPayment.get().getId().toString(), paymentId);
	}

	@After
	public void tearDown() {
		mongoTemplate.dropCollection(Payment.class);
	}
}
