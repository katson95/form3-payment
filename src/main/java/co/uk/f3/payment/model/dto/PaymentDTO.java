package co.uk.f3.payment.model.dto;

import co.uk.f3.payment.model.domain.Attribute;
import co.uk.f3.payment.utils.enums.ResourceType;
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
public class PaymentDTO {

	private String id;
	
	private ResourceType type;
	
	private int version;

	private String organisationId;
	
	private Attribute attribute;
	
	

}
