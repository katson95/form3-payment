package co.uk.f3.payment.service.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import co.uk.f3.payment.model.Payment;
import co.uk.f3.payment.service.IPaymentService;

@Service
public class PaymentServiceImpl implements IPaymentService {

	@Override
	public Optional<Payment> saveOrUpdatePayment(Payment payment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Payment> fetchPaymentByOrganisationId(String organisationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Payment> fetchPayments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePaymentByPaymentId(String paymentId) {
		// TODO Auto-generated method stub
		
	}

}
