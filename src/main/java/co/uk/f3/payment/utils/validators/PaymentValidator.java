package co.uk.f3.payment.utils.validators;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.everit.json.schema.Schema;
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

	public static void validateINput(Payment requestData) {
		final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);
		try {
			JsonNode requestDataJsonNode = JsonLoader.fromString(json(requestData));
			final JsonNode node = JsonLoader.fromResource("/schema.json");
			InputStream inputStream = new ByteArrayInputStream(node.toString().getBytes(StandardCharsets.UTF_8.name()));
			JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
			Schema schema = SchemaLoader.load(rawSchema);
			schema.validate(new JSONObject(requestDataJsonNode.toString()));

		} catch (Exception e) {
			LOGGER.error(e.getMessage().toString());
		}
	}
	
	private static String json(Object o) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(o);
    }

}
