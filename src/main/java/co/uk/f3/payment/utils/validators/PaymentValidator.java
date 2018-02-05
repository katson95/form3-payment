package co.uk.f3.payment.utils.validators;

import java.io.IOException;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.fge.jackson.JsonLoader;

import co.uk.f3.payment.controller.PaymentController;
import co.uk.f3.payment.model.domain.Payment;

public class PaymentValidator {

	public static void validateINput(Payment requestData) throws IOException {
		final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);
		try {
			JsonNode requestDataJsonNode = JsonLoader.fromString(json(requestData));
			
			JSONObject jsonSchema = new JSONObject(
					new JSONTokener(PaymentValidator.class.getResourceAsStream("/validation/schema/schema.json")));
			JSONObject jsonSubject = new JSONObject(
					new JSONTokener(requestDataJsonNode.toString()));
			
			Schema schema = SchemaLoader.load(jsonSchema);
			schema.validate(new JSONObject(jsonSubject));

		} catch (ValidationException e) {
			LOGGER.error(e.getMessage().toString());
		}
	}
	
	private static String json(Object o) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(o);
    }

}
