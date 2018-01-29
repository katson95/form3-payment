package co.uk.f3.payment.model.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Table(name = "FX")
public class Fx {

	@Column(name = "CONTRACT_REFERENCE")
	private String contractReference;

	@Column(name = "EXCHANGE_RATE")
	private String exchangeRate;

	@OneToOne(mappedBy = "fx", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Money originalFund;
}
