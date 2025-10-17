package io.github.marceltanuri.frameworks.restam3.http;

import java.util.Collections;
import java.util.Map;

/**
 * Represents an HTTP response.
 *
 * @author Marcel Tanuri
 */
public class HttpResponse {
    private final String body;
    private final HttpStatus status;
    private final Map<String, String> headers;

    /**
     * Creates a new HTTP response.
     *
     * @param body    the body of the response
     * @param status  the status of the response
     * @param headers the headers of the response
     */
    public HttpResponse(String body, HttpStatus status, Map<String, String> headers) {
        this.body = body;
        this.status = status;
        this.headers = headers;
    }

    /**
     * Creates a new HTTP response with default headers (Content-Type: application/json).
     *
     * @param body   the body of the response
     * @param status the status of the response
     */
    public HttpResponse(String body, HttpStatus status) {
        this(body, status, Collections.singletonMap("Content-Type", "application/json"));
    }

    /**
     * Creates a new HTTP response containing only the status (used for simple errors).
     * The body is formatted as a simple error JSON string using the status message.
     *
     * @param status the status of the response (and the base for the error message)
     */
    public HttpResponse(HttpStatus status) {
        this("{\"error\": \"" + status.getMessage() + "\"}", status, Collections.singletonMap("Content-Type", "application/json"));
    }

    /**
     * Gets the body of the response.
     *
     * @return the body of the response
     */
    public String getBody() {
        return body;
    }

    /**
     * Gets the status of the response.
     *
     * @return the status of the response
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * Gets the headers of the response.
     *
     * @return the headers of the response
     */
    public Map<String, String> getHeaders() {
        return headers;
    }
}