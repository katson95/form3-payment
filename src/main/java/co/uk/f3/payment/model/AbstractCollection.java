package co.uk.f3.payment.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.bson.types.ObjectId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractCollection {

	@Id
	private ObjectId id;

}
