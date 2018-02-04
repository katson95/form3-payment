package co.uk.f3.payment.model.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

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
@Document(collection = "ATTRIBUTE")
public class Attribute {

	@Field(value = "AMOUNT")
	private BigDecimal amount;

	@Field(value = "CURRENCY")
	private String currency;

	@Field(value = "CHARGES_INFORMATION")
	@JsonProperty("charges_information")
	private ChargesInformation chargesInformation;

	@Field(value = "END_TO_END_REFERENCE")
	@JsonProperty("end_to_end_reference")
	private String endOfEndPreference;

	@Field(value = "FX")
	private Fx fx;

	@Field(value = "NUMERIC_REFERENCE")
	@JsonProperty("numeric_reference")
	private String numericReference;

	@Field(value = "PAYMENT_ID")
	@JsonProperty("payment_id")
	private String paymentId;

	@Field(value = "PAYMENT_PURPOSE")
	@JsonProperty("payment_purpose")
	private String paymentPurpose;

	@Field(value = "PAYMENT_SCHEME")
	@JsonProperty("payment_scheme")
	private String paymentScheme;

	@Field(value = "PAYEMTN_TYPE")
	@JsonProperty("payment_type")
	private PaymentType paymentType;

	@Field(value = "PROCESSING_DATE")
	@JsonProperty("processing_date")
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate processingDate;

	@Field(value = "REFERENCE")
	@JsonProperty("reference")
	private String reference;

	@Field(value = "SCHEME_PAYMENT_TYPE")
	@JsonProperty("scheme_payment_type")
	private SchemePaymentType schemePaymentType;

	@Field(value = "SCHEME_PAYMENT_SUB_TYPE")
	@JsonProperty("scheme_payment_sub_type")
	private SchemePaymentType schemePaymentSubType;

	@Field(value = "BENEFICIARY_PARTY")
	@JsonProperty("beneficiary_party")
	private Party beneficiaryParty;

	@Field(value = "DEBITOR_PARTY")
	@JsonProperty("debtor_party")
	private Party debitorParty;

	@Field(value = "SPNSOR_PARTY")
	@JsonProperty("sponsor_party")
	private Party sponsorParty;

}
