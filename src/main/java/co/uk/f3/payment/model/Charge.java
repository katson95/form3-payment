package co.uk.f3.payment.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import co.uk.f3.payment.constants.ChargeType;
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
@Document(collection = "CHARGE")
public class Charge extends AbstractCollection {
	
	@Field(value = "CHARGE_TYPE")
	@Enumerated(EnumType.STRING)
	private ChargeType charge_type;
	
	@Field(value = "FUND")
	private Money fund;

}
