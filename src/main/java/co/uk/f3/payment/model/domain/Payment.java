package co.uk.f3.payment.model.domain;

import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import co.uk.f3.payment.utils.enums.ResourceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "PAYMENT")
public class Payment {

	@Id
	@Field
	@Builder.Default
	private String id = UUID.randomUUID().toString();

	@Field(value = "ORGANISATION_ID")
	private String organisationId;

	@Field(value = "TYPE")
	@Enumerated(EnumType.STRING)
	private ResourceType type;

	@Field(value = "ATTRIBUTE")
	private Attribute attribute;

	@Field(value = "VERSION")
	@Version()
	private int version;

}
