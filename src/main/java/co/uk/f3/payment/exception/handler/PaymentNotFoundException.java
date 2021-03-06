package co.uk.f3.payment.exception.handler;

public class PaymentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2859292084648724403L;
	private final String documentId;
	
	public PaymentNotFoundException(String id) {
		documentId = id;
	}
	
	public String getDocumentId() {
		return documentId;
	}

}
