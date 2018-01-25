package co.uk.f3.payment.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import co.uk.f3.payment.constants.ResourceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "PAYMENT")
public class Payment extends AbstractCollection {

	@Field(value = "ORGANISATION_ID")
	private String organisationId;
	
	@Field(value = "TYPE")
	@Enumerated(EnumType.STRING)
	private ResourceType type;
	
	@Field(value = "ATTRIBUTE")
	private Attribute attribute;
	
	@Field(value = "VERSION")
	private int version;

}
