package co.uk.f3.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.uk.f3.payment.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
