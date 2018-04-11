package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.scottyplunkett.server.ByteArraysReducer.merge;

class LogsHandler extends Producer {
    private HTTPRequest httpRequest;
    private String date;
    private final String authHeader = "WWW-Authenticate: Basic realm=\"Logs\"";
    private boolean authorized;
    private byte[] head;
    private byte[] body;
    private byte[] responseContent;

    LogsHandler() {}

    LogsHandler(HTTPRequest request, String _date) throws IOException {
        httpRequest = request;
        date = _date;
    }

    private boolean isAuthorized() {
        return "Basic YWRtaW46aHVudGVyMg==".equals(httpRequest.getAuthorization());
    }

    byte[] get() {
        return responseContent;
    }

    public void setHttpRequest(HTTPRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void produceContent() throws IOException {
        if(isAuthorized()) {
            head = (new HTTPResponseHeaders("200 OK", "text/html", date).get() + "\r\n").getBytes();
            body = Files.readAllBytes(Paths.get("Logs/logs.html"));
        } else {
            head = (new HTTPResponseHeaders("401 Unauthorized", "text/html", date).get() + authHeader + "\r\n\r\n").getBytes();
            body = "401 Unauthorized... Probably Above Your Paygrade.".getBytes();
        }
        responseContent = merge(body, head);
    }
}
