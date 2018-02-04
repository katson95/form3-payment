package co.uk.f3.payment.model.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonProperty("account_name")
	private String accountName;

	@Field(value = "ACCOUNT_NUMBER")
	@JsonProperty("account_number")
	private String accountNumber;

	@Field(value = "ACCOUNT_NUMBER_CODE")
	@JsonProperty("account_number_code")
	private String accountNumberCode;

	@Field(value = "PARTY_TYPE")
	@JsonProperty("party_type")
	private PartyType partType;

	@Field(value = "ACCOUNT_TYPE")
	@JsonProperty("account_type")
	private AccountType accountType;

	@Field(value = "ADDRESS")
	private String address;

	@Field(value = "BANK_ID")
	@JsonProperty("bank_id")
	private String bankId;

	@Field(value = "BANK_ID_CODE")
	@JsonProperty("bank_id_code")
	private String bankIdCode;

	@Field(value = "name")
	@JsonProperty("name")
	private String name;

}
