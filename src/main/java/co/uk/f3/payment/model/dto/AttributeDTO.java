package co.uk.f3.payment.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import co.uk.f3.payment.utils.enums.PaymentType;
import co.uk.f3.payment.utils.enums.SchemePaymentType;
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
public class AttributeDTO {

	private BigDecimal amount;

	private String currency;

	private ChargesInformationDTO chargesInformation;

	private String endOfEndPreference;

	private FxDTO fx;

	private String numericReference;

	private String paymentId;

	private String paymentPurpose;

	private String paymentScheme;

	private PaymentType paymentType;
	
	private LocalDateTime processingDate;

	private String reference;

	private SchemePaymentType schemePaymentType;

	private SchemePaymentType schemePaymentSubType;

	private PartyDTO beneficiaryParty;

	private PartyDTO debitorParty;

	private PartyDTO sponsorParty;

}
