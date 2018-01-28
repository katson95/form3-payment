package co.uk.f3.payment.model.domain;

import java.util.Collection;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
@Document(collection = "CHARGE_INFORMATION")
public class ChargesInformation {

	@Field(value = "BEARER_CODE")
	private String bearerCode;

	@Field(value = "CHARGES")
	private Collection<Charge> charges;
}
