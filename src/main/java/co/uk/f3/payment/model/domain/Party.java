package co.uk.f3.payment.model.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
@Document(collection = "PARTY")
public class Party {

	@Field(value = "ACCOUNT_NAME")
	private String accountName;

	@Field(value = "ACCOUNT_NUMBER")
	private String accountNumber;

	@Field(value = "ACCOUNT_NUMBER_CODE")
	private String accountNumberCode;

	@Field(value = "PARTY_TYPE")
	@Enumerated(EnumType.STRING)
	private PartyType partType;

	@Field(value = "ACCOUNT_TYPE")
	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	@Field(value = "ADDRESS")
	private String address;

	@Field(value = "BANK_ID")
	private String bankId;

	@Field(value = "BANK_ID_CODE")
	private String bankIdCode;

	@Transient
	private String name;

}
