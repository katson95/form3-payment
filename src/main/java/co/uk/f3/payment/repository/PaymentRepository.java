package co.uk.f3.payment.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.uk.f3.payment.model.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String> {

	public Optional<Payment> findPaymentByOrganisationId(String organisationId);

}
