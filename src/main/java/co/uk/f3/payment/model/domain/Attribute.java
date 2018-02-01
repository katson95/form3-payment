package co.uk.f3.payment.model.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import co.uk.f3.payment.model.AbstractCollection;
import co.uk.f3.payment.utils.converter.LocalDateTimeConverter;
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
@Entity
@Table(name = "ATTRIBUTE")
public class Attribute extends AbstractCollection {

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "CURRENCY")
	private String currency;

	@OneToOne(mappedBy = "attribute", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JsonManagedReference
	private ChargesInformation chargesInformation;

	@Column(name = "END_TO_END_REFERENCE")
	private String endOfEndPreference;

	@OneToOne(mappedBy = "attribute", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Fx fx;

	@Column(name = "NUMERIC_REFERENCE")
	private String numericReference;

	@Column(name = "PAYMENT_ID")
	private String paymentId;

	@Column(name = "PAYMENT_PURPOSE")
	private String paymentPurpose;

	@Column(name = "PAYMENT_SCHEME")
	private String paymentScheme;

	@Column(name = "PAYEMTN_TYPE")
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;

	@Column(name = "PROCESSING_DATE")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime processingDate;

	@Column(name = "REFERENCE")
	private String reference;

	@Column(name = "SCHEME_PAYMENT_TYPE")
	@Enumerated(EnumType.STRING)
	private SchemePaymentType schemePaymentType;

	@Column(name = "SCHEME_PAYMENT_SUB_TYPE")
	@Enumerated(EnumType.STRING)
	private SchemePaymentType schemePaymentSubType;

	@OneToOne(mappedBy = "attribute", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Party beneficiaryParty;

	@OneToOne(mappedBy = "attribute", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Party debitorParty;

	@OneToOne(mappedBy = "attribute", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Party sponsorParty;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID")
	@JsonBackReference
	private Payment payment;

}
