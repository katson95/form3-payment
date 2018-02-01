package co.uk.f3.payment.model.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import co.uk.f3.payment.model.AbstractCollection;
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
@Table(name = "CHARGE_INFORMATION")
public class ChargesInformation extends AbstractCollection{

	@Column(name = "BEARER_CODE")
	private String bearerCode;

	@OneToMany(mappedBy = "chargeInformation", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Collection<Charge> charges;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ATTRIBUTE_ID")
	@JsonBackReference
	private Attribute attribute;
}
