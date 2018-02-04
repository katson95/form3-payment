package co.uk.f3.payment.model.domain;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonProperty("id")
	@Builder.Default
	private String id = UUID.randomUUID().toString();

	@Field(value = "ORGANISATION_ID")
	@JsonProperty("organisation_id")
	private String organisationId;

	@Field(value = "TYPE")
	private ResourceType type;

	@Field(value = "ATTRIBUTE")
	private Attribute attributes;

	@Field(value = "VERSION")
	@Version()
	private int version;

}
