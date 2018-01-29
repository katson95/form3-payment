package co.uk.f3.payment.service;

import java.util.Optional;
import java.util.Set;

import co.uk.f3.payment.model.domain.Payment;

public interface IPaymentService {

	public Optional<Payment> saveOrUpdatePayment(Payment payment);

	public Optional<Payment> fetchPaymentById(String paymentId);

	public Set<Payment> fetchPayments();

	public void deletePaymentByPaymentId(String paymentId);
}
