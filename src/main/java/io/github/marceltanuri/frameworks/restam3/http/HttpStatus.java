package io.github.marceltanuri.frameworks.restam3.http;

/**
 * <p>Represents the HTTP status codes and their corresponding messages.</p>
 *
 * <p>This enumeration provides constants for common HTTP status codes,
 * organized by their class (1xx, 2xx, 3xx, 4xx, 5xx).</p>
 *
 * @author Marcel Tanuri
 * @since 1.0
 */
public enum HttpStatus{
    // 2xx Success
    /** 200 OK - The request has succeeded. */
    OK(200, "OK"),
    /** 201 Created - The request has been fulfilled and resulted in a new resource being created. */
    CREATED(201, "Created"),
    /** 202 Accepted - The request has been accepted for processing, but the processing has not been completed. */
    ACCEPTED(202, "Accepted"),
    /** 204 No Content - The server successfully processed the request, and is not returning any content. */
    NO_CONTENT(204, "No Content"),
    
    // 4xx Client Error
    /** 400 Bad Request - The server cannot process the request due to client error (e.g., malformed syntax). */
    BAD_REQUEST(400, "Bad Request"),
    /** 401 Unauthorized - The request requires user authentication. */
    UNAUTHORIZED(401, "Unauthorized"),
    /** 403 Forbidden - The server understood the request but refuses to authorize it. */
    FORBIDDEN(403, "Forbidden"),
    /** 404 Not Found - The server has not found anything matching the Request-URI. */
    NOT_FOUND(404, "Not Found"),
    /** 405 Method Not Allowed - The method specified in the Request-Line is not allowed for the resource identified by the Request-URI. */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    /** 408 Request Timeout - The client did not produce a request within the time that the server was prepared to wait. */
    REQUEST_TIMEOUT(408, "Request Timeout"),
    /** 409 Conflict - The request could not be completed because of a conflict with the current state of the resource. */
    CONFLICT(409, "Conflict"),
    /** 410 Gone - The requested resource is no longer available at the server. */
    GONE(410, "Gone"),
    /** 412 Precondition Failed - The precondition given in one or more request-header fields evaluated to false when tested on the server. */
    PRECONDITION_FAILED(412, "Precondition Failed"),
    /** 413 Payload Too Large - The server is refusing to process a request because the request entity is larger than the server is willing or able to process. */
    PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
    /** 414 URI Too Long - The server is refusing to service the request because the Request-URI is longer than the server is willing to interpret. */
    URI_TOO_LONG(414, "URI Too Long"),
    /** 415 Unsupported Media Type - The server is refusing to service the request because the entity of the request is in a format not supported by the requested resource for the requested method. */
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    /** 417 Expectation Failed - The expectation given in an Expect request-header field could not be met by the server. */
    EXPECTATION_FAILED(417, "Expectation Failed"),
    /** 418 I'm a teapot - Any attempt to brew coffee with a teapot should result in the error code 418 I'm a teapot. */
    IM_A_TEAPOT(418, "I'm a teapot"),
    /** 421 Misdirected Request - The request was directed at a server that is not able to produce a response. */
    MISDIRECTED_REQUEST(421, "Misdirected Request"),
    /** 422 Unprocessable Entity - The request was well-formed but was unable to be followed due to semantic errors. */
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
    /** 423 Locked - The resource that is being accessed is locked. */
    LOCKED(423, "Locked"),
    /** 424 Failed Dependency - The request failed because it depended on another request and that request failed. */
    FAILED_DEPENDENCY(424, "Failed Dependency"),
    /** 425 Too Early - The server is unwilling to risk processing a request that might be replayed. */
    TOO_EARLY(425, "Too Early"),
    /** 426 Upgrade Required - The client should switch to a different protocol. */
    UPGRADE_REQUIRED(426, "Upgrade Required"),
    /** 428 Precondition Required - The server requires the request to be conditional. */
    PRECONDITION_REQUIRED(428, "Precondition Required"),
    /** 429 Too Many Requests - The user has sent too many requests in a given amount of time. */
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    /** 451 Unavailable For Legal Reasons - The server is denying access to the resource as a consequence of a legal demand. */
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),

    // 5xx Server Error
    /** 500 Internal Server Error - A generic error message, given when an unexpected condition was encountered. */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    /** 501 Not Implemented - The server does not support the functionality required to fulfill the request. */
    NOT_IMPLEMENTED(501, "Not Implemented"),
    /** 503 Service Unavailable - The server is currently unable to handle the request due to a temporary overload or scheduled maintenance. */
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    /** 504 Gateway Timeout - The server was acting as a gateway or proxy and did not receive a timely response from the upstream server. */
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    /** 505 HTTP Version Not Supported - The server does not support the HTTP protocol version used in the request. */
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported");


    private final int code;
    private final String message;

    /**
     * Constructs an HttpStatus enum constant.
     *
     * @param code The numeric HTTP status code (e.g., 200, 404).
     * @param message The standard message associated with the status code (e.g., "OK", "Not Found").
     */
    HttpStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Retrieves the numeric HTTP status code.
     *
     * @return The integer status code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Retrieves the standard message for the HTTP status code.
     *
     * @return The status message string.
     */
    public String getMessage() {
        return message;
    }

}