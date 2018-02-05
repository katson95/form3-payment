package co.uk.f3.payment.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.uk.f3.payment.model.domain.Payment;
import co.uk.f3.payment.repository.IPaymentRepository;
import co.uk.f3.payment.service.IPaymentService;

/**
 * Class (Service) to allow execution of business logic operations.
 * 
 * @author Nissi Tafie
 *
 */
@Service
public class PaymentServiceImpl implements IPaymentService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private IPaymentRepository paymentRepository;

	@Autowired
	public void setPaymentRepository(IPaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;

	}

	/**
	 * Persists new Payment
	 * 
	 * @param to
	 *            be persisted payment
	 * @return created Payment
	 */
	@Override
	public Optional<Payment> savePayment(Payment payment) {
		return Optional.of(paymentRepository.insert(payment));
	}

	/**
	 * Updates existing Payment
	 * 
	 * @param to
	 *            be updated payment
	 * @return updated Payment
	 */
	@Override
	public Optional<Payment> updatePayment(Payment payment) {
		return Optional.of(paymentRepository.save(payment));
	}

	/**
	 * Fetches existing Payment by Id
	 * 
	 * @param id
	 *            of existing payment
	 * @return Payment
	 */
	@Override
	public Optional<Payment> fetchPaymentById(String id) {
		LOGGER.info(id);
		if (paymentRepository.exists(id)) {
			return Optional.of(paymentRepository.findOne(id));
		}
		return Optional.empty();
	}

	/**
	 * Fetches all existing payments.
	 * 
	 * @return list of existing Payments
	 */
	@Override
	public Set<Payment> fetchPayments() {
		return paymentRepository.findAll().stream().collect(Collectors.toSet());
	}

	/**
	 * Deletes existing Payment by Id
	 * 
	 * @param id
	 *            of existing payment
	 * @return result of operation
	 */
	@Override
	public boolean deletePaymentById(String id) {
		if (paymentRepository.exists(id)) {
			paymentRepository.delete(id);
			return true;
		}
		return false;

	}

}
