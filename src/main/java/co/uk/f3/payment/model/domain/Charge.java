package co.uk.f3.payment.model.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import co.uk.f3.payment.model.AbstractCollection;
import co.uk.f3.payment.utils.enums.ChargeType;
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
@Table(name = "CHARGE")
public class Charge extends AbstractCollection{

	@Column(name = "CHARGE_TYPE")
	@Enumerated(EnumType.STRING)
	private ChargeType chargeType;

	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "CURRENCY")
	private String currency;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHARGE_INFORMATION_ID")
	@JsonBackReference
	private ChargesInformation chargeInformation;

}
