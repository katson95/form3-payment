package co.uk.f3.payment.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@EqualsAndHashCode(callSuper=false)
@Data
@NoArgsConstructor
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Entity
@Table(name = "ACCOUNT")
public class Account extends AbstractEntity {

	private String name;
	private int number;
	private int numberCode;
	private String type;
	private Long bankId;
	private String bankIdCode;
}
