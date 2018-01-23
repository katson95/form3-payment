package co.uk.f3.payment.model;

import java.util.Date;

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
@Table(name = "USER")
public class User extends AbstractEntity{
	
	private String firstName;
	private String lastName;
	private String gender;
	private Date dateOfBirth;

}
