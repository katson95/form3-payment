package co.uk.f3.payment.controller;

import java.util.Optional;
import java.util.Set;

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

import co.uk.f3.payment.exception.handler.DocumentNotFoundException;
import co.uk.f3.payment.exception.handler.InvalidRequestException;
import co.uk.f3.payment.model.domain.Payment;
import co.uk.f3.payment.service.IPaymentService;

/**
 * Class (Controller) to allow execution of service operations by Client.
 * 
 * @author Nissi Tafie
 *
 */
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
	 * @param to
	 *            be persisted payment
	 * @return created Payment
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Payment> createPayment(@Validated @RequestBody Payment payment) {
		LOGGER.info("Creating new Payment: {}");
		try {
			Optional<Payment> paymentValue = paymentService.savePayment(payment);
			return new ResponseEntity<Payment>(paymentValue.get(), HttpStatus.CREATED);
		} catch (Exception ex) {
			throw new InvalidRequestException(ex.getMessage());
		}

	}

	/**
	 * Operation (Method) to create fetch Payment by id
	 * 
	 * @param id
	 *            of payment to be fetched
	 * @return fetched payment
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> fetchPaymentById(@PathVariable("id") String id) {
		LOGGER.info("fetching Payment : {}", id);
		Optional<Payment> optionalPayment = paymentService.fetchPaymentById(id);
		if (!optionalPayment.isPresent()) {
			LOGGER.error("Payment with id {} not found.", id);
			throw new DocumentNotFoundException(id);
		}

		Payment paymentValue = optionalPayment.get();
		return new ResponseEntity<Payment>(paymentValue, HttpStatus.OK);

	}

	/**
	 * Operation (Method) for updating Payment.
	 * 
	 * @param id
	 *            of payment to be fetched
	 * @param payment
	 *            to be updated
	 * @return updated payment
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePayment(@PathVariable("id") String id, @RequestBody Payment payment) {
		LOGGER.info("Updating Payment with id: {}", id);
		Optional<Payment> paymentValue = paymentService.updatePayment(payment);

		return new ResponseEntity<Payment>(paymentValue.get(), HttpStatus.OK);

	}

	/**
	 * Operation (Method) to fetch all Payments.
	 * 
	 * @return list of existing payments
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Set<Payment>> fetchpayments() {
		Set<Payment> payments = paymentService.fetchPayments();

		if (payments.isEmpty()) {
			return new ResponseEntity<Set<Payment>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Set<Payment>>(payments, HttpStatus.OK);
	}

	/**
	 * Operation (Method) to delete Payment.
	 * 
	 * @param id
	 *            of payment to be deleted
	 * @return operation outcome
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletePaymentById(@PathVariable("id") String id) {
		if (paymentService.deletePaymentById(id)) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		LOGGER.error("Payment with id {} not found.", id);
		throw new DocumentNotFoundException(id);
	}

}
