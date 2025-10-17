package io.github.marceltanuri.frameworks.restam3.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;

import java.io.Reader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.function.Consumer;

/**
 * Implementation of the JsonParser interface using Jackson, allowing
 * configuration of the ObjectMapper via a Consumer in the constructor.
 */
public class ConfigurableJacksonParser implements JsonParser {

    private final ObjectMapper objectMapper;

    /**
     * Constructor that allows configuring the Jackson ObjectMapper.
     *
     * @param configurer A function (Consumer) that receives an ObjectMapper
     * and applies desired configurations (e.g., enabling/disabling features).
     */
    public ConfigurableJacksonParser(Consumer<ObjectMapper> configurer) {
        this.objectMapper = new ObjectMapper();
        
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        if (configurer != null) {
            configurer.accept(this.objectMapper);
        }
    }

    /**
     * Default constructor that uses a basic Jackson configuration (no Pretty Print, etc.).
     */
    public ConfigurableJacksonParser() {
        this(null);
    }


    private JavaType getJavaType(Type typeOfT) {
        return objectMapper.getTypeFactory().constructType(typeOfT);
    }


    @Override
    public <T> T fromJson(String json, Type typeOfT) throws IOException {
        JavaType javaType = getJavaType(typeOfT);
        return objectMapper.readValue(json, javaType);
    }

    @Override
    public <T> T fromJson(Reader reader, Type typeOfT) throws IOException {
        JavaType javaType = getJavaType(typeOfT);
        return objectMapper.readValue(reader, javaType);
    }

    @Override
    public String toJson(Object src) throws JsonProcessingException {
        return objectMapper.writeValueAsString(src);
    }

    @Override
    public void toJson(Object src, Appendable writer) throws IOException {
        if (writer instanceof java.io.Writer) {
            objectMapper.writeValue((java.io.Writer) writer, src);
        } else {
            String json = toJson(src);
            writer.append(json);
        }
    }
}