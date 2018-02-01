package co.uk.f3.payment.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import co.uk.f3.payment.model.AbstractCollection;
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
@Entity
@Table(name = "PARTY")
public class Party extends AbstractCollection{

	@Column(name = "ACCOUNT_NAME")
	private String accountName;

	@Column(name = "ACCOUNT_NUMBER")
	private String accountNumber;

	@Column(name = "ACCOUNT_NUMBER_CODE")
	private String accountNumberCode;

	@Column(name = "PARTY_TYPE")
	@Enumerated(EnumType.STRING)
	private PartyType partType;

	@Column(name = "ACCOUNT_TYPE")
	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "BANK_ID")
	private String bankId;

	@Column(name = "BANK_ID_CODE")
	private String bankIdCode;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ATTRIBUTE_ID")
	@JsonBackReference
	private Attribute attribute;

	@Transient
	private String name;

}
