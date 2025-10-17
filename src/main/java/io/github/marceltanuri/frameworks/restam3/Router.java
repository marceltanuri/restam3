package io.github.marceltanuri.frameworks.restam3;

import io.github.marceltanuri.frameworks.restam3.controller.RestController;
import io.github.marceltanuri.frameworks.restam3.http.HttpRequest;
import io.github.marceltanuri.frameworks.restam3.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import io.github.marceltanuri.frameworks.restam3.http.HttpStatus;


/**
 * A simple router responsible for mapping HTTP request paths to the appropriate {@link RestController}
 * and dispatching the request for processing. It uses a Fluent Interface design for configuration.
 *
 * @author Marcel Tanuri
 */
public class Router {

    private final Map<String, RestController> routes = new HashMap<>();

    // Private constructor to enforce the use of the static factory method 'create()'.
    private Router() {
    }

    /**
     * Creates a new instance of the Router.
     * <p>
     * This static factory method should be used to initiate the method chaining
     * (Fluent Interface) for route configuration.
     *
     * @return A new Router instance.
     */
    public static Router create() {
        return new Router();
    }

    /**
     * Adds a single route, mapping a specific path to a controller.
     * <p>
     * This method returns the Router instance itself to support method chaining (Fluent Interface).
     *
     * @param path The route path (e.g., "/api/users").
     * @param controller The controller that will handle requests for this path.
     * @return The current Router instance, allowing further method calls.
     */
    public Router addRoute(String path, RestController controller) {
        routes.put(path, controller);
        return this;
    }

    /**
     * Adds multiple routes, mapping a list of paths to the same controller instance.
     * <p>
     * This method returns the Router instance itself to support method chaining (Fluent Interface).
     *
     * @param paths The list of paths to be mapped (e.g., Arrays.asList("/v1/data", "/v2/data")).
     * @param controller The controller that will handle requests for all these paths.
     * @return The current Router instance, allowing further method calls.
     */
    public Router addRoute(List<String> paths, RestController controller){
        paths.forEach(path -> addRoute(path, controller));
        return this;
    }

    /**
     * Handles an incoming HTTP request by parsing the request, finding the appropriate
     * controller based on the path, and dispatching the call to the corresponding HTTP method handler.
     *
     * @param clientSocket The client socket used to send the final response.
     * @param in The input reader (BufferedReader) containing the raw HTTP request data.
     * @throws IOException If an I/O error occurs while reading the request or sending the response.
     */
    public void handleRequest(Socket clientSocket, BufferedReader in) throws IOException {
        try {
            HttpRequest httpRequest = HttpRequest.from(in);

            String path = httpRequest.getPath();
            Optional<RestController> controller = routes.keySet().stream()
                .filter(path::startsWith)
                .findFirst()
                .map(routes::get);

            if (controller.isEmpty()) {
                _sendResponse(clientSocket, new HttpResponse("{\"error\": \"Not Found\"}", HttpStatus.NOT_FOUND));
                return;
            }
            
            HttpResponse response = switch (httpRequest.getMethod()) {
                case GET    -> controller.get().handleGet(httpRequest);
                case POST   -> controller.get().handlePost(httpRequest);
                case PATCH  -> controller.get().handlePatch(httpRequest);
                case PUT    -> controller.get().handlePut(httpRequest);
                case DELETE -> controller.get().handleDelete(httpRequest);
                default     -> new HttpResponse(HttpStatus.NOT_IMPLEMENTED);
            };

            _sendResponse(clientSocket, response);

        } catch (IOException | IllegalArgumentException e) {
            _sendResponse(clientSocket, new HttpResponse("{\"error\": \"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST));
        }
    }

    /**
     * Sends the complete HTTP response back to the client socket.
     *
     * @param clientSocket The client socket.
     * @param response The {@link HttpResponse} object to be serialized and sent.
     * @throws IOException If an I/O error occurs during transmission.
     */
    private void _sendResponse(Socket clientSocket, HttpResponse response) throws IOException {
        OutputStream out = clientSocket.getOutputStream();
        // Constructs the HTTP response string (Status Line, Headers, Body)
        String httpResponse = "HTTP/1.1 " + response.getStatus().getCode() + " " + response.getStatus().getMessage() + "\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: " + response.getBody().getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                "\r\n" +
                response.getBody();
        out.write(httpResponse.getBytes(StandardCharsets.UTF_8));
        out.flush();
    }
}