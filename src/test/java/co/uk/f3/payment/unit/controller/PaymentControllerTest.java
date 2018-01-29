package co.uk.f3.payment.unit.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import co.uk.f3.manager.PaymentCollectionGenerator;
import co.uk.f3.payment.controller.PaymentController;
import co.uk.f3.payment.model.domain.Payment;
import co.uk.f3.payment.service.IPaymentService;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

	public static final Logger LOGGER = LoggerFactory.getLogger(PaymentControllerTest.class);
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private IPaymentService paymentService;


	@Test
	public void fetchPayments_shouldReturnAllPayments() throws Exception {
		
		
		Payment p1 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment p2 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment p3 = PaymentCollectionGenerator.createBasicTestPayment();
		Payment p4 = PaymentCollectionGenerator.createBasicTestPayment();

		Set<Payment> payments = new HashSet<>(Arrays.asList(p1, p2, p3, p4));

		given(paymentService.fetchPayments()).willReturn(payments);
		

		mvc.perform(get("/payments/v1/")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(4)));
		verify(paymentService, VerificationModeFactory.times(1)).fetchPayments();
		reset(paymentService);
	}

}
