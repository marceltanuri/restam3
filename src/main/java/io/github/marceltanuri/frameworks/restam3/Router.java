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

import io.github.marceltanuri.frameworks.restam3.http.HttpStatus;


/**
 * A simple router for handling HTTP requests.
 *
 * @author Marcel Tanuri
 */
public class Router {

    private final Map<String, RestController> routes = new HashMap<>();

    /**
     * Creates a new Router.
     */
    public Router() {
    }

    /**
     * Adds a route to the router.
     *
     * @param path       the path of the route
     * @param controller the controller that handles the route
     */
    public void addRoute(String path, RestController controller) {
        routes.put(path, controller);
    }

    /**
     * Handles an incoming HTTP request.
     *
     * @param clientSocket the client socket
     * @param in           the input reader
     * @throws IOException if an I/O error occurs
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
                sendResponse(clientSocket, new HttpResponse("{\"error\": \"Not Found\"}", HttpStatus.NOT_FOUND));
                return;
            }

            HttpResponse response;
            switch (httpRequest.getMethod()) {
                case GET:
                    response = controller.get().handleGet(httpRequest);
                    break;
                case POST:
                    response = controller.get().handlePost(httpRequest);
                    break;
                case PATCH:
                    response = controller.get().handlePatch(httpRequest);
                    break;
                case PUT:
                    response = controller.get().handlePut(httpRequest);
                    break;
                case DELETE:
                    response = controller.get().handleDelete(httpRequest);
                    break;
                default:
                    response = new HttpResponse(HttpStatus.NOT_IMPLEMENTED);
                    break;
            }

            sendResponse(clientSocket, response);

        } catch (IOException | IllegalArgumentException e) {
            sendResponse(clientSocket, new HttpResponse("{\"error\": \"" + e.getMessage() + "\"}", HttpStatus.BAD_REQUEST));
        }
    }

    /**
     * Sends an HTTP response to the client.
     *
     * @param clientSocket the client socket
     * @param response     the HTTP response
     * @throws IOException if an I/O error occurs
     */
    private void sendResponse(Socket clientSocket, HttpResponse response) throws IOException {
        OutputStream out = clientSocket.getOutputStream();
        String httpResponse = "HTTP/1.1 " + response.getStatus().getCode() + " " + response.getStatus().getMessage() + "\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: " + response.getBody().getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                "\r\n" +
                response.getBody();
        out.write(httpResponse.getBytes(StandardCharsets.UTF_8));
        out.flush();
    }
}
