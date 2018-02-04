package co.uk.f3.payment.utils.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ResourceType {
	Payment("payment");
	
	@JsonProperty("value")
	private String value;

	ResourceType(String value) {
		this.value = value;
	}
}
