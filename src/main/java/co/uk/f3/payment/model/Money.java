package co.uk.f3.payment.model;

import java.math.BigDecimal;

import org.hibernate.annotations.Immutable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
@Document(collection = "MONEY")
@Immutable
public class Money extends AbstractCollection{

	@Field(value = "AMOUNT")
	private BigDecimal amount;

	@Field(value = "CURRENCY")
	private String currency;

}
