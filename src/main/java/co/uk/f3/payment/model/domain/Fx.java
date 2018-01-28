package co.uk.f3.payment.model.domain;

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
@Document(collection = "FX")
public class Fx {

	@Field(value = "CONTRACT_REFERENCE")
	private String contractReference;

	@Field(value = "EXCHANGE_RATE")
	private String exchangeRate;

	@Field(value = "ORIGINAL_FUND")
	private Money originalFund;
}
