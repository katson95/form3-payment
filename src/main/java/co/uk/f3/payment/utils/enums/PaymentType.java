package co.uk.f3.payment.utils.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentType {
	
	Credit("credit"), Debit("debit");
	
	@JsonProperty("value")
	private String value;

	PaymentType(String value) {
		this.value = value;
	}
}
