package io.github.marceltanuri.frameworks.restam3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple, lightweight HTTP server designed for RESTful APIs.
 * It uses Java's Virtual Threads (available in JDK 21+) to efficiently handle
 * concurrent client connections with minimal resource overhead.
 *
 * @author Marcel Tanuri
 */
public class HttpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    private final Router router;

    // Private constructor to enforce the use of the static factory method 'create()'.
    private HttpServer(Router router) {
        this.router = router;
    }

    /**
     * Factory method to create a new HttpServer instance.
     * <p>
     * This static method should be used to initiate the Fluent Interface pattern,
     * allowing the chaining of the {@code .start(port)} call.
     *
     * @param router The configured {@link Router} to use for mapping and dispatching requests.
     * @return A new, configured {@link HttpServer} instance.
     */
    public static HttpServer create(Router router) {
        return new HttpServer(router);
    }


    /**
     * Starts the HTTP server on the specified port.
     * <p>
     * The server uses a Virtual Thread executor to handle each incoming connection,
     * ensuring high concurrency and low thread overhead.
     *
     * @param port The port number for the server to listen on.
     */
    public void start(int port) {
        Banner.print();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            LOGGER.info("Server started on port {}", port);
            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                LOGGER.debug("New client connected");
                executor.submit(() -> _handleClient(clientSocket));
            }
        } catch (IOException e) {
            LOGGER.error("Error starting server", e);
        }
    }

    /**
     * Reads the client's request, delegates it to the router, and sends the resulting response.
     * <p>
     * This method is executed by a dedicated Virtual Thread for each client connection.
     *
     * @param clientSocket The client socket connection.
     */
    private void _handleClient(Socket clientSocket) {
        try (clientSocket) {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            router.handleRequest(clientSocket, in);
        } catch (IOException e) {
            LOGGER.error("Error handling client request", e);
        }
    }
}