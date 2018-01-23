package co.uk.f3.payment.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Embeddable
public class Attribute {

	private Long attributeId;
	private BigDecimal ammount;
	private BigDecimal chargesInformation;
	private String currency;
	private Date endOfEndPreference;
	private String fx;
	private String numericReference;
	private Long paymentId;
	private String paymentPurpose;
	private String paymentScheme;
	private String paymentType;
	private Date paymentDate;
	private String reference;
	private String schemePaymentSubType;
	private String schemePaymentType;

}
