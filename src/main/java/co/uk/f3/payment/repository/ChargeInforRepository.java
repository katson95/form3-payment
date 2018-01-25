package co.uk.f3.payment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.uk.f3.payment.model.ChargesInformation;

public interface ChargeInforRepository extends MongoRepository<ChargesInformation, String>{

}
