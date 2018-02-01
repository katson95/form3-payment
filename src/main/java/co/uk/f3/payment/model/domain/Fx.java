package co.uk.f3.payment.model.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import co.uk.f3.payment.model.AbstractCollection;
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
@Table(name = "FX")
public class Fx extends AbstractCollection {

	@Column(name = "CONTRACT_REFERENCE")
	private String contractReference;

	@Column(name = "EXCHANGE_RATE")
	private String exchangeRate;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@Column(name = "CURRENCY")
	private String currency;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ATTRIBUTE_ID")
	@JsonBackReference
	private Attribute attribute;
}
