package co.uk.f3.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import co.uk.f3.payment.model.domain.Attribute;
import co.uk.f3.payment.model.domain.Charge;
import co.uk.f3.payment.model.domain.ChargesInformation;
import co.uk.f3.payment.model.domain.Fx;
import co.uk.f3.payment.model.domain.Party;
import co.uk.f3.payment.model.domain.Payment;
import co.uk.f3.payment.utils.enums.AccountType;
import co.uk.f3.payment.utils.enums.PartyType;
import co.uk.f3.payment.utils.enums.PaymentType;
import co.uk.f3.payment.utils.enums.ResourceType;
import co.uk.f3.payment.utils.enums.SchemePaymentType;


public class PaymentCollectionGenerator {
	
	public static Payment createBasicTestPayment(){
		Attribute attribute = PaymentCollectionGenerator.buildTestAttribute();
		return Payment.builder()
				.organisationId(UUID.randomUUID().toString())
				.attributes(attribute)
				.type(ResourceType.Payment)
				.build();
	}
	
	public static Payment createBasicTestPaymentWithId(String id){
		Attribute attribute = PaymentCollectionGenerator.buildTestAttribute();
		return Payment.builder()
				.id(id)
				.organisationId(UUID.randomUUID().toString())
				.attributes(attribute)
				.type(ResourceType.Payment)
				.build();
	}
	

	public static Payment createBasicTestPaymentWithOrganisationId(String id, String organisationId){
		Attribute attribute = PaymentCollectionGenerator.buildTestAttribute();
		return Payment.builder()
				.id(id)
				.organisationId(organisationId)
				.attributes(attribute)
				.type(ResourceType.Payment)
				.build();
	}


	private static Party buildTestBeneficiaryParty(PartyType type) {
		return Party.builder()
				.accountName("W Owens")
				.accountNumber("31926819")
				.accountNumberCode("BBAN")
				.accountType(AccountType.PAYMENT)
				.partType(type)
				.address("1 The Beneficiary Localtown SE2")
				.bankId("403000")
				.bankIdCode("GBDSC")
				.name("Wilfred Jeremiah Owens")
				.build();
	}
	
	private static Party buildTestDebitorParty(PartyType type) {
		return Party.builder()
				.accountName("EJ Brown Black")
				.accountNumber("GB29XABC10161234567801")
				.accountNumberCode("IBAN")
				.accountType(AccountType.PAYMENT)
				.partType(type)
				.address("10 Debtor Crescent Sourcetown NE1")
				.bankId("203301")
				.bankIdCode("GBDSC")
				.name("Emelia Jane Brown")
				.build();
	}
	
	private static Party buildTestSponsorParty(PartyType type) {
		return Party.builder()
				.accountNumber("56781234")
				.partType(type)
				.bankId("123123")
				.bankIdCode("GBDSC")
				.build();
	}
	
	private static Charge buildTestCharge(BigDecimal amount, String currencyCode) {
		return Charge.builder()
				.amount(new BigDecimal("100.21"))
				.currency("GBP")
				.build();
		
	}
	
	private static Fx buildTestFx() {
		return Fx.builder()
				.contractReference("FX123")
				.exchangeRate("2.00000")
				.amount(new BigDecimal("100.21"))
				.currency("GBP")
				.build();
	}
	
	private static ChargesInformation buildTestChargesInformation() {
		Charge senderCharge1 = buildTestCharge(new BigDecimal("5.00"), "GBP");
		Charge senderCharge2 = buildTestCharge(new BigDecimal("10.00"), "USD");
		Charge receivererCharge = buildTestCharge(new BigDecimal("1.00"), "GBP");
		return ChargesInformation.builder()
				.bearerCode("SHAR")
				.charges(new HashSet<>(Arrays.asList(senderCharge1, senderCharge2, receivererCharge)))
				.build();
	}
	
	private static Attribute buildTestAttribute() {
		ChargesInformation charges_information = buildTestChargesInformation();
		Fx forex = buildTestFx();
		Party beneficiaryParty = buildTestBeneficiaryParty(PartyType.BENEFICIARY);
		Party debitorParty = buildTestDebitorParty(PartyType.DEBTOR);
		Party sponsorParty = buildTestSponsorParty(PartyType.SPONSOR);
		return Attribute.builder()
				.amount(new BigDecimal("100.21"))
				.currency("GBP")
				.beneficiaryParty(beneficiaryParty)
				.chargesInformation(charges_information)
				.debitorParty(debitorParty)
				.endOfEndPreference("Wil piano Jan")
				.fx(forex)
				.numericReference("1002001")
				.paymentId("123456789012345678")
				.paymentPurpose("Paying for goods/services")
				.paymentScheme("FPS")
				.paymentType(PaymentType.Credit)
				.processingDate(LocalDate.now())
				.reference("Payment for Em's piano lessons")
				.schemePaymentSubType(SchemePaymentType.InternetBanking)
				.schemePaymentType(SchemePaymentType.ImmediatePayment)
				.sponsorParty(sponsorParty)
				.build();
	}
}
