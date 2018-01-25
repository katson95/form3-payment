package co.uk.f3.payment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.uk.f3.payment.model.Attribute;

public interface AttributeRepository extends MongoRepository<Attribute, String> {

}
