package co.uk.f3.payment.model;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Convert;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import co.uk.f3.payment.constants.PaymentType;
import co.uk.f3.payment.constants.SchemePaymentType;
import co.uk.f3.payment.converter.LocalDateTimeConverter;
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
public class Attribute extends AbstractCollection {

	@Field(value = "FUND")
	private Money fund;

	@Field(value = "ATTRIBUTE_ID")
	private Long attribute_id;

	@Field(value = "CHARGES_INFORMATION")
	private ChargesInformation charges_information;

	@Field(value = "CREATE_DATE")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime end_of_end_preference;

	@Field(value = "FX")
	private Fx fx;

	@Field(value = "NUMERIC_REFERENCE")
	private String numeric_reference;

	@Field(value = "PAYMENT_ID")
	private String payment_id;

	@Field(value = "PAYMENT_PURPOSE")
	private String payment_purpose;

	@Field(value = "PAYMENT_SCHEME")
	private String payment_scheme;
	
	@Field(value = "PAYEMTN_TYPE")
	@Enumerated(EnumType.STRING)
	private PaymentType payment_type;

	@Field(value = "PROCESSING_DATE")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime processing_date;

	@Field(value = "REFERENCE")
	private String reference;

	@Field(value = "SCHEME_PAYMENT_TYPE")
	@Enumerated(EnumType.STRING)
	private SchemePaymentType scheme_payment_type;

	@Field(value = "SCHEME_PAYMENT_SUB_TYPE")
	@Enumerated(EnumType.STRING)
	private SchemePaymentType scheme_payment_sub_type;

	@Field(value = "PARTIES")
	private Collection<Party> parties;

}
