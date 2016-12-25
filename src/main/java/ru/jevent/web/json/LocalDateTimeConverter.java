package ru.jevent.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.jevent.util.TimeUtil;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeConverter {
    public static class SettingSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime ldt, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeString(TimeUtil.toString(ldt));
        }

        @Override
        public Class<LocalDateTime> handledType() {
            return LocalDateTime.class;
        }
    }

    public static class SettingDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctx) throws IOException, JsonProcessingException {
            return TimeUtil.toDateTime(jp.getText());
        }

        @Override
        public Class<LocalDateTime> handledType() {
            return LocalDateTime.class;
        }
    }
}
