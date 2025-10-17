package io.github.marceltanuri.frameworks.restam3.controller;

import io.github.marceltanuri.frameworks.restam3.http.HttpRequest;
import io.github.marceltanuri.frameworks.restam3.http.HttpResponse;
import io.github.marceltanuri.frameworks.restam3.http.HttpStatus;
import io.github.marceltanuri.frameworks.restam3.json.JsonParser; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 * A REST base controller providing common HTTP request handling and
 * JSON serialization/deserialization capabilities via dependency injection.
 *
 * @author Marcel Tanuri
 */
public abstract class RestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestController.class);
    
    private final JsonParser jsonParser; 

    /**
     * Constructs the RestController, injecting the required JsonParser implementation.
     * @param jsonParser The configured JSON parser (e.g., JacksonParser).
     */
    public RestController(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    /**
     * Handles GET requests.
     *
     * @param request the HTTP request
     * @return the HTTP response
     */
    public HttpResponse handleGet(HttpRequest request) {
        return new HttpResponse(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Handles POST requests.
     *
     * @param request the HTTP request
     * @return the HTTP response
     */
    public HttpResponse handlePost(HttpRequest request) {
        return new HttpResponse(HttpStatus.NOT_IMPLEMENTED);
    }


    /**
     * Handles PATCH requests.
     *
     * @param request the HTTP request
     * @return the HTTP response
     */
    public HttpResponse handlePatch(HttpRequest request) {
        return new HttpResponse(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Handles PUT requests.
     *
     * @param request the HTTP request
     * @return the HTTP response
     */
    public HttpResponse handlePut(HttpRequest request) {
        return new HttpResponse(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Handles DELETE requests.
     *
     * @param request the HTTP request
     * @return the HTTP response
     */
    public HttpResponse handleDelete(HttpRequest request) {
        return new HttpResponse(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * Converts a Java object into its JSON string representation using the injected parser.
     *
     * @param object The object to convert.
     * @return An Optional containing the JSON string, or empty if a serialization error occurs.
     */
    protected Optional<String> _toJson(Object object) {
        try {
            return Optional.of(jsonParser.toJson(object)); 
        } catch (Exception e) {
            LOGGER.error("Error converting object to JSON", e);
            return Optional.empty();
        }
    }

    /**
     * Converts a JSON string into a Java object of the specified type using the injected parser.
     *
     * Note: Using {@code java.lang.reflect.Type} allows for deserializing complex
     * types like generic collections (e.g., List&lt;T&gt;).
     *
     * @param <T> The target object type.
     * @param json The JSON input string.
     * @param typeOfT The destination type (e.g., {@code MyClass.class} or a {@code TypeReference} for collections).
     * @return An Optional containing the deserialized object.
     */
    protected <T> Optional<T> _fromJson(String json, Type typeOfT) {
        try {
            return Optional.of(jsonParser.fromJson(json, typeOfT));
        } catch (Exception e) {
            // Logging translated to English
            LOGGER.error("Error converting JSON to object", e);
            return Optional.empty();
        }
    }

    /**
     * Sends an HTTP error response using only the status code.
     * The response body will be automatically generated with a simple JSON error message.
     * @param status The HTTP status code to return.
     * @return The resulting HttpResponse object.
     */
    protected HttpResponse _sendError(HttpStatus status) {
        return new HttpResponse(status);
    }

    /**
     * Sends an HTTP error response with a custom message and status code.
     * The custom message is serialized into the response body as JSON.
     * @param message The custom error message.
     * @param status The HTTP status code to return.
     * @return The resulting HttpResponse object.
     */
    protected HttpResponse _sendError(String message, HttpStatus status) {
        // Serializes the custom message into JSON.
        String errorJson = _toJson(new ErrorMessage(message)).orElse("{\"error\":\"Serialization Error\"}");
        return new HttpResponse(errorJson, status);
    }
    
    /**
     * Simple utility class for serializing error messages into a standardized JSON format.
     */
    private static class ErrorMessage {
        public String error;
        /**
         * @param message The error message to wrap.
         */
        public ErrorMessage(String message) {
            this.error = message;
        }
    }
}