package edu.horb.dhbw.config.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.horb.dhbw.datacore.commonstructure.NamedElement;
import edu.horb.dhbw.datacore.packages.Model;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class NamedElementDeserializer extends StdDeserializer<NamedElement> {
    public NamedElementDeserializer() {

        this(null);
    }

    public NamedElementDeserializer(Class<?> vc) {

        super(vc);
    }

    @Override
    public NamedElement deserialize(JsonParser jp, DeserializationContext context)
            throws IOException, JsonProcessingException {

        JsonNode node = jp.getCodec().readTree(jp);
        Model model = (Model) jp.getCodec().readValue(jp, Model.class);
        node.toString();
        return null;
    }
}
