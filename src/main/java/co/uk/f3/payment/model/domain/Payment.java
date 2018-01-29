package co.uk.f3.payment.model.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Version;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import co.uk.f3.payment.model.AbstractCollection;
import co.uk.f3.payment.utils.enums.ResourceType;
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
@Table(name = "PAYMENT")
public class Payment extends AbstractCollection {
	
	@Column(name = "PAYMENT_ID")
	@NotNull
	private String paymentId;

	@Column(name = "ORGANISATION_ID")
	@NotNull
	private String organisationId;
	
	@Column(name = "TYPE")
	@Enumerated(EnumType.STRING)
	private ResourceType type;
	
	@OneToOne(mappedBy = "payment", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JsonManagedReference
	private Attribute attribute;
	
	@Version
	@Column(name="VERSION")
	private int version;

}
