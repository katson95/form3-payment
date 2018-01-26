package co.uk.f3.payment.service;

import java.util.Optional;
import java.util.Set;

import co.uk.f3.payment.model.Payment;

public interface IPaymentService {

	public Optional<Payment> saveOrUpdatePayment(Payment payment);

	public Optional<Payment> fetchPaymentByOrganisationId(String organisationId);

	public Set<Payment> fetchPayments();

	public void deletePaymentByPaymentId(String paymentId);
}
