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

@Service
public class PaymentServiceImpl implements IPaymentService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private IPaymentRepository paymentRepository;

	@Autowired
	public void setPaymentRepository(IPaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;

	}

	@Override
	public Optional<Payment> saveOrUpdatePayment(Payment payment) {
		return Optional.of(paymentRepository.save(payment));
	}

	@Override
	public Optional<Payment> fetchPaymentById(String paymentId) {
		LOGGER.info(paymentId);
		return paymentRepository.findById(paymentId);
	}

	@Override
	public Set<Payment> fetchPayments() {
		return paymentRepository.findAll().stream().collect(Collectors.toSet());
	}

	@Override
	public void deletePaymentByPaymentId(String paymentId) {
		paymentRepository.deleteById(paymentId);

	}

}
