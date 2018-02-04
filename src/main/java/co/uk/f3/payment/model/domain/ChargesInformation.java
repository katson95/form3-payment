package co.uk.f3.payment.model.domain;

import java.math.BigDecimal;
import java.util.Collection;

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
@Document(collection = "CHARGE_INFORMATION")
public class ChargesInformation {

	@Field(value = "BEARER_CODE")
	@JsonProperty("bearer_code")
	private String bearerCode;
	
	
	@Field(value = "SENDER_CHARGES")
	@JsonProperty("sender_charges")
	private Collection<Charge> charges;
	
	@Field(value = "RECEIVER_CHARGES_AMOUNT")
	@JsonProperty("receiver_charges_amount")
	private BigDecimal receiverChargesAmount;
	
	@Field(value = "RECEIVER_CHARGES_CURRENCY")
	@JsonProperty("receiver_charges_currency")
	private String receiverChargesCurrency;
}

