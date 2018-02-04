package co.uk.f3.payment.utils.converter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomDateSerializer extends StdSerializer<Date> {

    private static final long serialVersionUID = -2894356342227378312L;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    
    public CustomDateSerializer() {
        super(LocalDateTime.class, false);
    }

    public CustomDateSerializer(final Class<Date> t) {
        super(t);
    }

    @Override
    public void serialize(final Date value, final JsonGenerator gen, final SerializerProvider arg2) throws IOException, JsonProcessingException {
        gen.writeString(formatter.format(value));
    }
    
//    @Override
//    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
//        return LocalDateTime.parse(p.getValueAsString()); // or overloaded with an appropriate format
//    }
}