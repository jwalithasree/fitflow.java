package controller;

import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * FitFlow Academic AOP Project
 * Embedded HTTP Server using Java's built-in HttpServer (com.sun.net.httpserver).
 *
 * OOP Concept: Server Lifecycle Management, Concurrency with Thread Pools,
 * and Delegation to specialized handlers.
 */
public class FitFlowServer {
    public static final int DEFAULT_PORT = 8080;
    private final int port;
    private final String projectRoot;
    private HttpServer server;

    private int boundPort;

    public FitFlowServer(int port, String projectRoot) {
        this.port = port;
        this.boundPort = port;
        this.projectRoot = projectRoot;
    }

    public void start() throws IOException {
        int currentPort = this.port;
        boolean bound = false;
        
        while (!bound && currentPort < this.port + 20) {
            try {
                server = HttpServer.create(new InetSocketAddress(currentPort), 0);
                this.boundPort = currentPort;
                bound = true;
            } catch (java.net.BindException be) {
                System.out.println("[NOTE] Port " + currentPort + " is in use. Trying port " + (currentPort + 1) + "...");
                currentPort++;
            }
        }

        if (!bound) {
            throw new IOException("Could not bind to any port in range " + this.port + " - " + (currentPort - 1));
        }

        // Bind REST API endpoints
        server.createContext("/api", new ApiHandler());

        // Bind Static Web Asset handler (HTML, CSS, JS)
        server.createContext("/", new StaticFileHandler(projectRoot));

        // Multi-threaded request execution using standard Java Executor
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();

        System.out.println("===============================================================");
        System.out.println("       FITFLOW - ADVANCED OOP FITNESS & WELLNESS PLATFORM     ");
        System.out.println("===============================================================");
        System.out.println(" >> Java HTTP Server successfully running on: http://localhost:" + boundPort);
        System.out.println(" >> Serving web assets from: " + new File(projectRoot).getAbsolutePath());
        System.out.println(" >> REST API endpoints active under: http://localhost:" + boundPort + "/api/");
        System.out.println(" >> Pure Java logic: BMI, Water, Calories, Workout & Nutrition");
        System.out.println("===============================================================");
    }

    public void stop() {
        if (server != null) {
            server.stop(1);
            System.out.println("FitFlow server stopped.");
        }
    }

    public int getPort() {
        return boundPort;
    }
}
