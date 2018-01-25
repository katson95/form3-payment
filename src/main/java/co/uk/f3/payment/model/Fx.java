package co.uk.f3.payment.model;

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
public class Fx extends AbstractCollection{

	@Field(value = "CONTRACT_REFERENCE")
	private String contract_reference;
	
	@Field(value = "EXCHANGE_RATE")
	private String exchange_rate;
	
	@Field(value = "ORIGINAL_FUND")
	private Money original_fund;
}
