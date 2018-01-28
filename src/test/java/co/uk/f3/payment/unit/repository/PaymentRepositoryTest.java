package co.uk.f3.payment.unit.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.uk.f3.payment.model.domain.Payment;
import co.uk.f3.payment.repository.IPaymentRepository;
import co.uk.f3.payment.utils.enums.ResourceType;

public class PaymentRepositoryTest {

	public static final Logger LOGGER = LoggerFactory.getLogger(PaymentRepositoryTest.class);
	
	private IPaymentRepository paymentRepositoryMock;

	@Before
	public void setUpMock() {
		paymentRepositoryMock = mock(IPaymentRepository.class);
	}

	@Test
	public void test_findPaymentByOrganisationIdShouldReturnPayment() throws Exception {
		String paymentId = UUID.randomUUID().toString();
		Payment payment = Payment.builder()
				.type(ResourceType.PAYMENT)
				.paymentId(paymentId)
				.build();
		Optional<Payment> paymentValue = Optional.of(payment);
		when(paymentRepositoryMock.findPaymentByPaymentId(paymentId)).thenReturn(paymentValue);
	}

}
