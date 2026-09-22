package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * FitFlow Academic AOP Project
 * HTTP Handler for serving static frontend assets (HTML, CSS, JS, Images).
 *
 * OOP Concept: Interface implementation (HttpHandler), Exception handling, and I/O streams.
 */
public class StaticFileHandler implements HttpHandler {
    private final Path rootDir;

    public StaticFileHandler(String projectRoot) {
        this.rootDir = Paths.get(projectRoot).toAbsolutePath().normalize();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();

        // Default route redirects to index.html
        if (requestPath.equals("/") || requestPath.isEmpty()) {
            requestPath = "/web/index.html";
        } else if (!requestPath.startsWith("/web/") && !requestPath.startsWith("/css/") && !requestPath.startsWith("/js/")) {
            // Check if file exists under web/
            if (Files.exists(rootDir.resolve("web" + requestPath))) {
                requestPath = "/web" + requestPath;
            }
        }

        // Remove leading slash for path resolution
        String relativePath = requestPath.startsWith("/") ? requestPath.substring(1) : requestPath;
        Path targetFile = rootDir.resolve(relativePath).normalize();

        // Security check: Prevent directory traversal attack
        if (!targetFile.startsWith(rootDir) || !Files.exists(targetFile) || Files.isDirectory(targetFile)) {
            String errorMsg = "<h1>404 Not Found</h1><p>Resource not found: " + requestPath + "</p>";
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            byte[] bytes = errorMsg.getBytes("UTF-8");
            exchange.sendResponseHeaders(404, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
            return;
        }

        // Determine MIME Content-Type
        String mimeType = determineMimeType(targetFile.getFileName().toString());
        exchange.getResponseHeaders().set("Content-Type", mimeType);

        byte[] fileBytes = Files.readAllBytes(targetFile);
        exchange.sendResponseHeaders(200, fileBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(fileBytes);
        }
    }

    private String determineMimeType(String filename) {
        String lower = filename.toLowerCase();
        if (lower.endsWith(".html") || lower.endsWith(".htm")) return "text/html; charset=UTF-8";
        if (lower.endsWith(".css")) return "text/css; charset=UTF-8";
        if (lower.endsWith(".js")) return "application/javascript; charset=UTF-8";
        if (lower.endsWith(".json")) return "application/json; charset=UTF-8";
        if (lower.endsWith(".png")) return "image/png";
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return "image/jpeg";
        if (lower.endsWith(".svg")) return "image/svg+xml";
        if (lower.endsWith(".ico")) return "image/x-icon";
        return "text/plain; charset=UTF-8";
    }
}
