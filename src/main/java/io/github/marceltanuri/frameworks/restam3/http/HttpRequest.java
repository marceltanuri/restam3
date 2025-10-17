package io.github.marceltanuri.frameworks.restam3.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an HTTP request.
 *
 * @author Marcel Tanuri
 */
public class HttpRequest {

    private HttpMethod method;
    private String path;
    private String httpVersion;
    private Map<String, String> headers;
    private String body;

    /**
     * Creates a new HTTP request.
     *
     * @param method      the HTTP method
     * @param path        the path of the request
     * @param httpVersion the HTTP version
     * @param headers     the headers of the request
     * @param body        the body of the request
     */
    private HttpRequest(HttpMethod method, String path, String httpVersion, Map<String, String> headers, String body) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
        this.headers = headers;
        this.body = body;
    }

    /**
     * Creates a new HTTP request from a BufferedReader.
     *
     * @param reader the reader to read the request from
     * @return the new HTTP request
     * @throws IOException if an I/O error occurs
     */
    public static HttpRequest from(BufferedReader reader) throws IOException {
        String requestLine = reader.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IOException("Invalid request: empty request line");
        }

        String[] requestParts = requestLine.split(" ");
        if (requestParts.length < 2) {
            throw new IOException("Invalid request line: " + requestLine);
        }
        HttpMethod method = HttpMethod.valueOf(requestParts[0]);
        String path = requestParts[1];
        String httpVersion = (requestParts.length == 3) ? requestParts[2] : "HTTP/1.1";

        Map<String, String> headers = new HashMap<>();
        String headerLine;
        while ((headerLine = reader.readLine()) != null && !headerLine.isEmpty()) {
            String[] headerParts = headerLine.split(": ", 2);
            if (headerParts.length == 2) {
                headers.put(headerParts[0], headerParts[1]);
            }
        }

        StringBuilder bodyBuilder = new StringBuilder();
        if (headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            char[] buffer = new char[1024];
            int bytesRead = 0;
            while (bytesRead < contentLength) {
                int read = reader.read(buffer, 0, Math.min(buffer.length, contentLength - bytesRead));
                if (read == -1) {
                    break;
                }
                bodyBuilder.append(buffer, 0, read);
                bytesRead += read;
            }
        }

        return new HttpRequest(method, path, httpVersion, headers, bodyBuilder.toString());
    }

    /**
     * Gets the HTTP method.
     *
     * @return the HTTP method
     */
    public HttpMethod getMethod() {
        return method;
    }

    /**
     * Gets the path of the request.
     *
     * @return the path of the request
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the HTTP version.
     *
     * @return the HTTP version
     */
    public String getHttpVersion() {
        return httpVersion;
    }

    /**
     * Gets the headers of the request.
     *
     * @return the headers of the request
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Gets the body of the request.
     *
     * @return the body of the request
     */
    public String getBody() {
        return body;
    }
}
