package co.uk.f3.manager;

import java.util.UUID;

import co.uk.f3.payment.constants.ResourceType;
import co.uk.f3.payment.model.Attribute;
import co.uk.f3.payment.model.Payment;


public class PaymentCollectionGenerator {

	public static Payment createBasicTestPayment(){
		Attribute attribute = PaymentCollectionGenerator.buildTestAttribute();
		return Payment.builder()
				.organisationId(UUID.randomUUID().toString())
				.attribute(attribute)
				.type(ResourceType.PAYMENT)
				.version(1)
				.build();
	}
	
	public static Payment createBasicTestPaymentWithOrganisationId(String organisationId){
		Attribute attribute = PaymentCollectionGenerator.buildTestAttribute();
		return Payment.builder()
				.organisationId(UUID.randomUUID().toString())
				.attribute(attribute)
				.type(ResourceType.PAYMENT)
				.version(1)
				.build();
	}

	private static Attribute buildTestAttribute() {
		// TODO Auto-generated method stub
		return null;
	}
}
