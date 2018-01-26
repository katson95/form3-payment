package co.uk.f3.payment.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import co.uk.f3.payment.constants.ResourceType;
import co.uk.f3.payment.model.Payment;
import co.uk.f3.payment.service.impl.PaymentServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

	private PaymentServiceImpl underTestImpl;
	
	private IPaymentService underTest;

	@Before
	public void setupMock() {
		underTest = mock(IPaymentService.class);
	}

	@Test
	public void test_createPayment_shouldReturnNewPayment() throws Exception {
		Payment payment = Payment.builder().type(ResourceType.PAYMENT).organisationId(UUID.randomUUID().toString())
				.build();
		Optional<Payment> paymentValue = Optional.of(payment);
		when(underTest.saveOrUpdatePayment(payment)).thenReturn(paymentValue);

	}

	@Test
	public void test_fetchPaymentByOrganisationId_shouldReturnPayment() throws Exception {
		String organisationId = UUID.randomUUID().toString();
		Payment payment = Payment.builder().type(ResourceType.PAYMENT).organisationId(organisationId).build();

		Optional<Payment> paymentValue = Optional.of(payment);

		when(underTest.fetchPaymentByOrganisationId(organisationId)).thenReturn(paymentValue);
	}

	@Test
	public void test_updatePayment_shouldReturnUpdatedPayment() throws Exception {

	}

	@Test
	public void test_fetchPayments_shouldReturnAllPayments() throws Exception {
		Payment payment1 = Payment.builder().type(ResourceType.PAYMENT).organisationId(UUID.randomUUID().toString())
				.build();
		Payment payment2 = Payment.builder().type(ResourceType.PAYMENT).organisationId(UUID.randomUUID().toString())
				.build();

		Set<Payment> payments = new HashSet<>(Arrays.asList(payment1, payment2));

		when(underTest.fetchPayments()).thenReturn(payments);

	}

	@Test
	public void test_deletePayment_shouldDeletePaymentById() throws Exception {
		String paymentId = UUID.randomUUID().toString();
		doNothing().when(underTest).deletePaymentByPaymentId(paymentId);
		
		@SuppressWarnings("unused")
		IPaymentService pService = Mockito.mock(IPaymentService.class);
		
		underTestImpl.deletePaymentByPaymentId(paymentId);
		
		verify(underTest, times(1)).deletePaymentByPaymentId(paymentId);
		
		
	}
}
