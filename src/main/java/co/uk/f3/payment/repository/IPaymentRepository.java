package co.uk.f3.payment.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.uk.f3.payment.model.domain.Payment;

public interface IPaymentRepository extends MongoRepository<Payment, String> {

	Optional<Payment> findPaymentByPaymentId(String paymentId);}
