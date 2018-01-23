package co.uk.f3.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.uk.f3.payment.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
