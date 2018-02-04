package co.uk.f3.payment.model.domain;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonProperty("contract_reference")
	private String contractReference;

	@Field(value = "EXCHANGE_RATE")
	@JsonProperty("exchange_rate")
	private String exchangeRate;

	@Field(value = "AMOUNT")
	@JsonProperty("original_amount")
	private BigDecimal amount;
	
	@Field(value = "CURRENCY")
	@JsonProperty("original_currency")
	private String currency;
}
