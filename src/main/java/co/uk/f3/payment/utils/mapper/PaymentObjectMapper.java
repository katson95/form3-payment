package co.uk.f3.payment.utils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import co.uk.f3.payment.model.domain.Payment;
import co.uk.f3.payment.model.dto.PaymentDTO;

public class PaymentObjectMapper {
	
	@Autowired
	private ModelMapper mapper;
	
	public PaymentDTO mapToPaymentDTO(Payment payment) {
		PaymentDTO paymentDTO = mapper.map(payment, PaymentDTO.class);
		return paymentDTO;
	}
	
	public Payment mapToPaymentDocument(PaymentDTO paymentDTO) {
		Payment payment = mapper.map(paymentDTO, Payment.class);
		return payment;
	}
}
