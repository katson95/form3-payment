package co.uk.f3.payment.controller;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.uk.f3.payment.exception.handler.EntityNotFoundException;
import co.uk.f3.payment.model.domain.Payment;
import co.uk.f3.payment.service.IPaymentService;

@RestController
@RequestMapping(value = "/payments/v1")
public class PaymentController {

	public static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

	private IPaymentService paymentService;

	public PaymentController(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	/**
	 * Operation (Method) to create new PaymentDTO
	 * 
	 * @param PaymentDTO
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> createPayment(@Validated @RequestBody Payment payment) {
		LOGGER.info("Creating new PaymentDTO: {}");
		Optional<Payment> paymentValue = paymentService.saveOrUpdatePayment(payment);
		return new ResponseEntity<Payment>(paymentValue.get(), HttpStatus.CREATED);
	}

	/**
	 * Operation (Method) to create fetch Payment by id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> fetchPaymentById(@PathVariable("id") String id) {
		LOGGER.info("fetching Payment : {}", id);
		Optional<Payment> optionalPayment = paymentService.fetchPaymentById(id);
		if (!optionalPayment.isPresent()) {
			LOGGER.error("Payment with id {} not found.", id);
			throw new EntityNotFoundException(id);
		}

		Payment paymentValue = optionalPayment.get();
		return new ResponseEntity<Payment>(paymentValue, HttpStatus.OK);

	}

	/**
	 * Operation (Method) for updating Payment.
	 * 
	 * @param id
	 * @param Payment
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePayment(@PathVariable("id") String id,
			@RequestBody Payment payment) {
		LOGGER.info("Updating Payment with id: {}", id);
		Optional<Payment> paymentValue = paymentService.saveOrUpdatePayment(payment);

		return new ResponseEntity<Payment>(paymentValue.get(), HttpStatus.OK);

	}

	/**
	 * Operation (Method) to fetch all Payments.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Set<Payment>> fetchpayment() {
		Set<Payment> payments = paymentService.fetchPayments().stream().collect(Collectors.toSet());

		if (payments.isEmpty()) {
			return new ResponseEntity<Set<Payment>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Set<Payment>>(payments, HttpStatus.OK);
	}

	/**
	 * Operation (Method) to delete Payment.
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletePaymentByid(@PathVariable("id") String id) {
		paymentService.deletePaymentById(id);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
