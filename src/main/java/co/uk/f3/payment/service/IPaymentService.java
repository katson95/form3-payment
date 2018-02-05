package co.uk.f3.payment.service;

import java.util.Optional;
import java.util.Set;

import co.uk.f3.payment.model.domain.Payment;

public interface IPaymentService {

	public Optional<Payment> savePayment(Payment payment);
	
	public Optional<Payment> updatePayment(Payment payment);

	public Optional<Payment> fetchPaymentById(String id);

	public Set<Payment> fetchPayments();

	public boolean deletePaymentById(String id);
}
