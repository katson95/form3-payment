package co.uk.f3.payment.utils.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SchemePaymentType {

	InternetBanking("InternetBanking"), ImmediatePayment("immediate_banking");

	@JsonProperty("value")
	private String value;

	SchemePaymentType(String value) {
		this.value = value;
	}
}
