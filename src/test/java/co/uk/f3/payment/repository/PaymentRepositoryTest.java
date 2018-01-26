package co.uk.f3.payment.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import co.uk.f3.payment.constants.ResourceType;
import co.uk.f3.payment.model.Payment;

@RunWith(MockitoJUnitRunner.class)
public class PaymentRepositoryTest {

	@Autowired
	private PaymentRepository paymentRepositoryMock;

	@Before
	public void setUpMock() {
		paymentRepositoryMock = mock(PaymentRepository.class);
	}

	@Test
	public void test_findPaymentByOrganisationIdShouldReturnPayment() throws Exception {
		String organisationId = UUID.randomUUID().toString();
		Payment payment = Payment.builder()
				.type(ResourceType.PAYMENT)
				.organisationId(organisationId)
				.build();
		Optional<Payment> paymentValue = Optional.of(payment);
		when(paymentRepositoryMock.findPaymentByOrganisationId(organisationId)).thenReturn(paymentValue);
	}

}
