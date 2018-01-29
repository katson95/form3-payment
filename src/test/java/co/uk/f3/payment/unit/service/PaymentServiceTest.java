package co.uk.f3.payment.unit.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.uk.f3.payment.model.domain.Payment;
import co.uk.f3.payment.repository.IPaymentRepository;
import co.uk.f3.payment.service.impl.PaymentServiceImpl;
import co.uk.f3.payment.utils.enums.ResourceType;

public class PaymentServiceTest {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceTest.class);

	private PaymentServiceImpl underTest;

	@Mock
    private Payment payment;

	@Mock
	private IPaymentRepository paymentRepository;

	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
		underTest = new PaymentServiceImpl();
		underTest.setPaymentRepository(paymentRepository);
	}

	@Test
	public void saveOrUpdatePayment_shouldReturnNewOrUpdatedPayment() throws Exception {

		when(paymentRepository.save(payment)).thenReturn(payment);
		Optional<Payment> savedPayment = underTest.saveOrUpdatePayment(payment);
		assertThat(savedPayment.get(), is(equalTo(payment)));

	}

	@Test
	public void fetchPaymentBypaymentId_shouldReturnPayment() throws Exception {
		ObjectId id = new ObjectId(new Date());
		Payment payment = Payment.builder().type(ResourceType.PAYMENT).id(id).build();

		Optional<Payment> paymentValue = Optional.of(payment);

		when(underTest.fetchPaymentById(id.toString())).thenReturn(paymentValue);
	}

	@Test
	public void fetchPayments_shouldReturnAllPayments() throws Exception {
		Payment payment1 = Payment.builder().type(ResourceType.PAYMENT).id(new ObjectId(new Date()))
				.build();
		Payment payment2 = Payment.builder().type(ResourceType.PAYMENT).id(new ObjectId(new Date()))
				.build();

		List<Payment> payments = new ArrayList<>(Arrays.asList(payment1, payment2));

		when(underTest.fetchPayments().stream().collect(Collectors.toList())).thenReturn(payments);

	}

	@Test
	public void deletePayment_shouldDeletePaymentById() throws Exception {
		String paymentId = UUID.randomUUID().toString();
		doNothing().when(paymentRepository).deleteById(paymentId);

		underTest.deletePaymentById(paymentId);

		verify(paymentRepository, times(1)).deleteById(paymentId);

	}
}
