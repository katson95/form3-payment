package co.uk.f3.payment.utils.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AccountType {
	PAYMENT(0), SAVING(1);

	@JsonProperty("code")
	private int code;

	private AccountType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
