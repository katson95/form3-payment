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
	public ResponseEntity<?> createPaymentDTO(@Validated @RequestBody Payment payment) {
		LOGGER.info("Creating new PaymentDTO: {}");
		Optional<Payment> paymentValue = paymentService.saveOrUpdatePayment(payment);
		return new ResponseEntity<Payment>(paymentValue.get(), HttpStatus.CREATED);
	}

	/**
	 * Operation (Method) to create fetch PaymentDTO by paymentId
	 * 
	 * @param paymentId
	 * @return
	 */
	@RequestMapping(value = "/{paymentId}", method = RequestMethod.GET)
	public ResponseEntity<?> fetchPaymentById(@PathVariable("paymentId") String paymentId) {
		LOGGER.info("fetching PaymentDTO : {}", paymentId);
		Optional<Payment> optionalPayment = paymentService.fetchPaymentById(paymentId);
		if (!optionalPayment.isPresent()) {
			LOGGER.error("PaymentDTO with paymentId {} not found.", paymentId);
			throw new EntityNotFoundException(paymentId);
		}

		Payment paymentValue = optionalPayment.get();
		return new ResponseEntity<Payment>(paymentValue, HttpStatus.OK);

	}

	/**
	 * Operation (Method) for updating PaymentDTO.
	 * 
	 * @param paymentId
	 * @param PaymentDTO
	 * @return
	 */
	@RequestMapping(value = "/{paymentId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePaymentDTO(@PathVariable("paymentId") String paymentId, @RequestBody Payment payment) {
		LOGGER.info("Updating PaymentDTO with id: {}", paymentId);
		Optional<Payment> paymentValue = paymentService.saveOrUpdatePayment(payment);

		return new ResponseEntity<Payment>(paymentValue.get(), HttpStatus.OK);

	}

	/**
	 * Operation (Method) to fetch all paymentDTOs.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Set<Payment>> fetchpaymentDTOs() {
		Set<Payment> payments = paymentService.fetchPayments().stream().collect(Collectors.toSet());

		if (payments.isEmpty()) {
			return new ResponseEntity<Set<Payment>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Set<Payment>>(payments, HttpStatus.OK);
	}

	/**
	 * Operation (Method) to delete PaymentDTO.
	 * 
	 * @param paymentId
	 * @return
	 */
	@RequestMapping(value = "/{paymentId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletePaymentDTOBypaymentId(@PathVariable("paymentId") String paymentId) {
		paymentService.deletePaymentByPaymentId(paymentId);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
