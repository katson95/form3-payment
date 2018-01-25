package co.uk.f3.payment.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import co.uk.f3.payment.constants.AccountType;
import co.uk.f3.payment.constants.PartyType;
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
public class Party extends AbstractCollection{
	
	@Field(value = "ACCOUNT_NAME")
	private String account_name;
	
	@Field(value = "ACCOUNT_NUMBER")
	private String account_number;
	
	@Field(value = "ACCOUNT_NUMBER_CODE")
	private String account_number_code;
	
	@Field(value = "PARTY_TYPE")
	@Enumerated(EnumType.STRING)
	private PartyType part_type;
	
	@Field(value = "ACCOUNT_TYPE")
	@Enumerated(EnumType.STRING)
	private AccountType account_type;
	
	@Field(value = "ADDRESS")
	private String address;
	
	@Field(value = "BANK_ID")
	private String bank_id;
	
	@Field(value = "BANK_ID_CODE")
	private String bank_id_code;
	
	@Transient
	private String name;
	
}
