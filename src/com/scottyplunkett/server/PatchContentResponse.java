package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class PatchContentResponse {
    private String responseContent;
    private String headers;
    private String body;

    private String patchTag = "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec";
    private String defaultTag = "5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0";
    private final byte[] defaultBytes = "default content".getBytes();
    private final byte[] patchBytes = "patched content".getBytes();
    private final Path path = Paths.get("public/patch-content.txt");

    PatchContentResponse(HTTPRequest request, String date) throws IOException {
        String requestLine = request.getRequestLine();
        String requestMethod = requestLine.split("\\s")[0];
        if("PATCH".equals(requestMethod)) {
            headers = "HTTP/1.1 204\r\nDate: " + date + "\r\nContent-Type: text/plain\r\n";
            body = "";
            writeToFile(request.getEtag());
        } else {
            headers = "HTTP/1.1 200 OK\r\nDate: " + date + "\r\nContent-Type: text/plain\r\n";
            body = new HTTPResponseBody(request.getRequestLine()).get();
        }
        responseContent = headers + "\r\n" + body;
    }

    String get() {
        return responseContent;
    }

    private void writeToFile(String eTag) throws IOException {
        byte[] contentBytes = eTag.equals(patchTag) ? patchBytes : defaultBytes;
        Files.write(path, contentBytes);
    }
}
