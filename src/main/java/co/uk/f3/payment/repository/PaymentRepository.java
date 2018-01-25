package co.uk.f3.payment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.uk.f3.payment.model.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String> {

}
