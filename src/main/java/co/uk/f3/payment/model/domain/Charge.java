package co.uk.f3.payment.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

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
public class Charge {

	@Column(name = "CHARGE_TYPE")
	@Enumerated(EnumType.STRING)
	private ChargeType chargeType;

	@Column(name = "FUND")
	private Money fund;

}
