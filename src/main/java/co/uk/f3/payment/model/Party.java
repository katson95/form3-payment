package co.uk.f3.payment.model;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Embeddable
public class Party {
	
	private Long partyId;
	private String partyType;
	private String name;
	private String bank;
	private Address address;
	private Account account;

}
