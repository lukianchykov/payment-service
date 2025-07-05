package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {

    private static final String STATIC_DIR = "simple-http-server/static";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started at http://localhost:8080");

        while (true) {
            try (Socket clientSocket = serverSocket.accept()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out = clientSocket.getOutputStream();

                String requestLine = in.readLine();
                if (requestLine == null || requestLine.isEmpty()) {
                    continue;
                }

                System.out.println("Request: " + requestLine);
                String[] parts = requestLine.split(" ");
                if (parts.length < 2) {
                    sendResponse(out, 400, "Bad Request", "text/plain", "Bad Request".getBytes());
                    continue;
                }

                String path = parts[1];
                String fileName = path.substring(path.lastIndexOf('/') + 1);

                Path baseDir = Paths.get(STATIC_DIR).toAbsolutePath().normalize();
                Path filePath = baseDir.resolve(fileName).normalize();

                if (!filePath.startsWith(baseDir)) {
                    sendResponse(out, 403, "Forbidden", "text/plain", "Forbidden".getBytes());
                    continue;
                }

                if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                    byte[] content = Files.readAllBytes(filePath);
                    String contentType = getContentType(fileName);
                    sendResponse(out, 200, "OK", contentType, content);
                } else {
                    sendResponse(out, 404, "Not Found", "text/plain", "File Not Found".getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendResponse(OutputStream out, int statusCode, String statusText, String contentType, byte[] content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        writer.write("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
        writer.write("Content-Type: " + contentType + "\r\n");
        writer.write("Content-Length: " + content.length + "\r\n");
        writer.write("\r\n");
        writer.flush();
        out.write(content);
        out.flush();
    }

    private static String getContentType(String fileName) {
        Map<String, String> mimeTypes = new HashMap<>();
        mimeTypes.put("html", "text/html");
        mimeTypes.put("css", "text/css");
        mimeTypes.put("js", "application/javascript");
        mimeTypes.put("png", "image/png");
        mimeTypes.put("jpg", "image/jpeg");
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("gif", "image/gif");
        mimeTypes.put("txt", "text/plain");

        int dotIndex = fileName.lastIndexOf('.');
        String extension = (dotIndex >= 0) ? fileName.substring(dotIndex + 1).toLowerCase() : "";

        return mimeTypes.getOrDefault(extension, "application/octet-stream");
    }
}
