package co.uk.f3.payment.controller;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.uk.f3.payment.exception.handler.EntityNotFoundException;
import co.uk.f3.payment.model.Payment;
import co.uk.f3.payment.service.IPaymentService;

@RestController
@RequestMapping(value = "/payments")
public class PaymentController {

	public static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);

	private IPaymentService paymentService;

	public PaymentController(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	/**
	 * Operation (Method) to create new Payment
	 * 
	 * @param payment
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createPayment(@Validated @RequestBody Payment payment) {
		LOG.info("Creating new Payment: {}");
		Optional<Payment> paymentValue = paymentService.saveOrUpdatePayment(payment);
		return new ResponseEntity<>(paymentValue.get(), HttpStatus.CREATED);
	}

	/**
	 * Operation (Method) to create fetch Payment by OrganisationId
	 * 
	 * @param organisationId
	 * @return
	 */
	@RequestMapping(value = "/{organisationId}", method = RequestMethod.GET)
	public ResponseEntity<?> fetchPaymentByOrnaginsationId(@PathVariable("organisationId") String organisationId) {
		LOG.info("fetching Payment : {}", organisationId);
		Optional<Payment> optionalPayment = paymentService.fetchPaymentByOrganisationId(organisationId);
		if (!optionalPayment.isPresent()) {
			LOG.error("Payment with organisationId {} not found.", organisationId);
			throw new EntityNotFoundException(organisationId);
		}

		Payment paymentValue = optionalPayment.get();
		return new ResponseEntity<Payment>(paymentValue, HttpStatus.OK);

	}

	/**
	 * Operation (Method) for updating Payment.
	 * 
	 * @param paymentId
	 * @param payment
	 * @return
	 */
	@RequestMapping(value = "/{paymentId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePayment(@PathVariable("paymentId") String paymentId, @RequestBody Payment payment) {
		LOG.info("Updating Payment with id: {}", paymentId);

		Optional<Payment> paymentValue = paymentService.saveOrUpdatePayment(payment);

		return new ResponseEntity<Payment>(paymentValue.get(), HttpStatus.OK);

	}

	/**
	 * Operation (Method) to fetch all payments.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<Payment>> fetchPayments() {
		Set<Payment> payments = paymentService.fetchPayments().stream().collect(Collectors.toSet());

		if (payments.isEmpty()) {
			return new ResponseEntity<Set<Payment>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Set<Payment>>(payments, HttpStatus.OK);
	}

	/**
	 * Operation (Method) to delete Payment.
	 * 
	 * @param paymentId
	 * @return
	 */
	@RequestMapping(value = "/{paymentId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletePaymentByPaymentId(@PathVariable("paymentId") String paymentId) {
		paymentService.deletePaymentByPaymentId(paymentId);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
