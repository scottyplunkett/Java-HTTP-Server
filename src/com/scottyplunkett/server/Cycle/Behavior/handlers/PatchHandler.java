package com.scottyplunkett.server.Cycle.Behavior.handlers;

import com.scottyplunkett.server.Cycle.Request.HTTPRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PatchHandler extends Handler {
    private String responseContent;
    private String headers;
    private String body;
    private HTTPRequest httpRequest;
    private String date;
    private String patchTag = "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec";
    private String defaultTag = "5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0";
    private final byte[] defaultBytes = "default content".getBytes();
    private final byte[] patchBytes = "patched content".getBytes();
    private final Path path = Paths.get("public/patch-content.txt");

    public PatchHandler() {}

    public PatchHandler(HTTPRequest request, String date) throws IOException {
        setHttpRequest(request);
        setDate(date);

    }

    public byte[] get() {
        return responseContent.getBytes();
    }

    private void writeToFile(String eTag) throws IOException {
        byte[] contentBytes = eTag.equals(patchTag) ? patchBytes : defaultBytes;
        Files.write(path, contentBytes);
    }

    public void setHttpRequest(HTTPRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void produceContent() throws IOException {
        String requestLine = httpRequest.getRequestLine();
        String requestMethod = requestLine.split("\\s")[0];
        if ("PATCH".equals(requestMethod)) {
            produce204();
        } else {
            produce200();
        }
        responseContent = headers + "\r\n" + body;
    }

    private void produce200() throws IOException {
        headers = "HTTP/1.1 200 OK\r\nDate: " + date + "\r\nContent-Type: text/plain\r\n";
        body = new HTTPResponseBody(httpRequest.getRequestLine()).get();
    }

    private void produce204() throws IOException {
        headers = "HTTP/1.1 204\r\nDate: " + date + "\r\nContent-Type: text/plain\r\n";
        body = "";
        writeToFile(httpRequest.getEtag());
    }
}
