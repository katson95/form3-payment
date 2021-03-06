package co.uk.f3.payment.exception.handler;

import org.springframework.validation.Errors;

public class InvalidRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Errors errors;

	public InvalidRequestException(String message) {
		super(message);
	}

	public Errors getErrors() {
		return errors;
	}
}
