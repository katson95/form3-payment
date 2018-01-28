package co.uk.f3.payment.model.dto;

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
public class ChargeDTO {

	private ChargeType chargeType;

	private MoneyDTO fund;

}
