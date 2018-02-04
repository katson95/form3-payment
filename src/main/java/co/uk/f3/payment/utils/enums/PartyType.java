package co.uk.f3.payment.utils.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PartyType {
	BENEFICIARY("beneficiary_party"), DEBTOR("debtor_party"), SPONSOR("sponsor_party");

	@JsonProperty("value")
	private String value;

	PartyType(String value) {
		this.value = value;
	}
}
