package co.uk.f3.payment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.uk.f3.payment.model.Fx;

public interface FxRepository extends MongoRepository<Fx, String> {

}
