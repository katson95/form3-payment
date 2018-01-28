package co.uk.f3.payment.component.repository;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;
import java.util.UUID;

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
import co.uk.f3.payment.repository.IPaymentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentRespositoryTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private IPaymentRepository underTest;

	@ClassRule
	public static DockerComposeRule docker = DockerComposeRule.builder().file("src/test/resources/docker-compose.yml")
			.build();

	@Before
	public void startUp() {
		mongoTemplate.dropCollection(Payment.class);
	}

	@Test
	public void fetchPaymentByOrganisationId_shouldReturnExistingPayment() throws Exception {
		String paymentId = UUID.randomUUID().toString();
		Payment payment = PaymentCollectionGenerator.createBasicTestPaymentWithPaymentId(paymentId);
		mongoTemplate.insert(payment);

		Optional<Payment> savedPayment = underTest.findPaymentByPaymentId(paymentId);

		assertNotNull(savedPayment.get().getId());
		assertNotNull(savedPayment.get().getOrganisationId(), paymentId);
	}

	@After
	public void tearDown() {
		mongoTemplate.dropCollection(Payment.class);
	}
}
