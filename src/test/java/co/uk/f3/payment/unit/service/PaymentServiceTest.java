package co.uk.f3.payment.unit.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.uk.f3.payment.model.domain.Payment;
import co.uk.f3.payment.repository.IPaymentRepository;
import co.uk.f3.payment.service.impl.PaymentServiceImpl;
import co.uk.f3.utils.PaymentCollectionGenerator;

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
		when(paymentRepository.insert(payment)).thenReturn(payment);
		Optional<Payment> savedPayment = underTest.savePayment(payment);
		assertThat(savedPayment.get(), is(equalTo(payment)));

	}

	@Test
	public void fetchPaymentBypaymentId_shouldReturnPayment() throws Exception {
		String id = UUID.randomUUID().toString();
		Payment payment = PaymentCollectionGenerator.createBasicTestPaymentWithId(id);

		Optional<Payment> paymentValue = Optional.of(payment);
		when(paymentRepository.findOne(payment.getId().toString())).thenReturn(paymentValue.get());
		when(paymentRepository.exists(payment.getId().toString())).thenReturn(true);

		when(underTest.fetchPaymentById(payment.getId())).thenReturn(paymentValue);
	}

	@Test
	public void fetchPayments_shouldReturnAllPayments() throws Exception {
		Payment payment1 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment payment2 = PaymentCollectionGenerator.createBasicTestPayment();

		List<Payment> payments = new ArrayList<>(Arrays.asList(payment1, payment2));

		when(underTest.fetchPayments().stream().collect(Collectors.toList())).thenReturn(payments);

	}

	@Test
	public void deletePayment_shouldDeletePaymentById() throws Exception {
		String id = UUID.randomUUID().toString();
		when(paymentRepository.exists(id)).thenReturn(true);
		when(underTest.deletePaymentById(payment.getId())).thenReturn(true);

	}

	@Test
	public void ValidPaymentInput_shouldNotThrowValidationExceptionWhenPaymentIsInvalid() throws ValidationException {
		JSONObject jsonSchema = new JSONObject(
				new JSONTokener(PaymentServiceTest.class.getResourceAsStream("/validation/schema/schema.json")));
		JSONObject jsonSubject = new JSONObject(
				new JSONTokener(PaymentServiceTest.class.getResourceAsStream("/validation/data/payment.json")));

		Schema schema = SchemaLoader.load(jsonSchema);
		schema.validate(jsonSubject);
	}

	@Test(expected = ValidationException.class)
	public void ValidPaymentInput_shouldThrowValidationExceptionWhenPaymentIsInvalid() {

		JSONObject jsonSchema = new JSONObject(
				new JSONTokener(PaymentServiceTest.class.getResourceAsStream("/validation/schema/schema.json")));
		JSONObject jsonSubject = new JSONObject(
				new JSONTokener(PaymentServiceTest.class.getResourceAsStream("/validation/data/invalid_payment.json")));

		Schema schema = SchemaLoader.load(jsonSchema);
		schema.validate(jsonSubject);
	}
}
