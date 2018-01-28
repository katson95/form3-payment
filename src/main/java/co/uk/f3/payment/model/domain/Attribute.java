package co.uk.f3.payment.model.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import co.uk.f3.payment.utils.converter.LocalDateTimeConverter;
import co.uk.f3.payment.utils.enums.PaymentType;
import co.uk.f3.payment.utils.enums.SchemePaymentType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
import lombok.NoArgsConstructor;

@Builder
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "ATTRIBUTE")
public class Attribute {

	@Field(value = "AMOUNT")
	private BigDecimal amount;

	@Field(value = "CURRENCY")
	private String currency;

	@Field(value = "CHARGES_INFORMATION")
	private ChargesInformation chargesInformation;

	@Field(value = "END_TO_END_REFERENCE")
	private String endOfEndPreference;

	@Field(value = "FX")
	private Fx fx;

	@Field(value = "NUMERIC_REFERENCE")
	private String numericReference;

	@Field(value = "PAYMENT_ID")
	private String paymentId;

	@Field(value = "PAYMENT_PURPOSE")
	private String paymentPurpose;

	@Field(value = "PAYMENT_SCHEME")
	private String paymentScheme;

	@Field(value = "PAYEMTN_TYPE")
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;

	@Field(value = "PROCESSING_DATE")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime processingDate;

	@Field(value = "REFERENCE")
	private String reference;

	@Field(value = "SCHEME_PAYMENT_TYPE")
	@Enumerated(EnumType.STRING)
	private SchemePaymentType schemePaymentType;

	@Field(value = "SCHEME_PAYMENT_SUB_TYPE")
	@Enumerated(EnumType.STRING)
	private SchemePaymentType schemePaymentSubType;

	@Field(value = "BENEFICIARY_PARTY")
	private Party beneficiaryParty;

	@Field(value = "DEBITOR_PARTY")
	private Party debitorParty;

	@Field(value = "SPNSOR_PARTY")
	private Party sponsorParty;

}
