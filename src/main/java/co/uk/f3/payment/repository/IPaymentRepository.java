package co.uk.f3.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.uk.f3.payment.model.domain.Payment;

public interface IPaymentRepository extends JpaRepository<Payment, String> {}
