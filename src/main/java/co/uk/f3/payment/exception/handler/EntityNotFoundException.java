package co.uk.f3.payment.exception.handler;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2859292084648724403L;
	private final Long entityId;
	
	public EntityNotFoundException(Long id) {
		entityId = id;
	}
	
	public EntityNotFoundException(String id) {
		entityId = Long.parseLong(id);
	}
	
	public Long getEntityId() {
		return entityId;
	}

}
