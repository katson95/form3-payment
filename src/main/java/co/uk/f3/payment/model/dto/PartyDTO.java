package co.uk.f3.payment.model.dto;

import co.uk.f3.payment.utils.enums.AccountType;
import co.uk.f3.payment.utils.enums.PartyType;
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
public class PartyDTO {

	private String accountName;

	private String accountNumber;

	private String accountNumberCode;

	private PartyType partType;

	private AccountType accountType;

	private String address;

	private String bankId;

	private String bankIdCode;

	private String name;

}
