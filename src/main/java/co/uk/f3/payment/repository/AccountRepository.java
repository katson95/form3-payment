package co.uk.f3.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.uk.f3.payment.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

}
