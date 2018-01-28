package co.uk.f3.payment.utils.enums;

public enum AccountType {
	PAYMENT(0), SAVING(1);

	private int code;

	private AccountType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
