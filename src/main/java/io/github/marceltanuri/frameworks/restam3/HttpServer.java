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
 * A simple HTTP server.
 *
 * @author Marcel Tanuri
 */
public class HttpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    private final Router router;

    /**
     * Creates a new HttpServer.
     *
     * @param router the router to use for handling requests
     */
    public HttpServer(Router router) {
        this.router = router;
    }

    /**
     * Starts the HTTP server on the specified port.
     *
     * @param port the port to listen on
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
                executor.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            LOGGER.error("Error starting server", e);
        }
    }

    /**
     * Handles a client connection.
     *
     * @param clientSocket the client socket
     */
    private void handleClient(Socket clientSocket) {
        try (clientSocket) {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            router.handleRequest(clientSocket, in);
        } catch (IOException e) {
            LOGGER.error("Error handling client request", e);
        }
    }
}
