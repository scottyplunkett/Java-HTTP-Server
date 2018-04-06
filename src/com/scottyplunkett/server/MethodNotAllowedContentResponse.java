package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;

class MethodNotAllowedContentResponse extends Producer {
    HTTPRequest httpRequest;
    String date;
    String produced;

    MethodNotAllowedContentResponse() {}

    MethodNotAllowedContentResponse(HTTPRequest request, String _date) throws IOException {
        httpRequest = request;
        date = _date;
    }

    private String produce405Response() throws IOException {
        String contentType = Files.probeContentType(Router.route(httpRequest.getRequestLine()));
        String head = new HTTPResponseHeaders("405 Method Not Allowed", contentType, date).get();
        head = head + "Allow: GET\r\n";
        String payLoad = "405: Method Not Allowed... Stick to what you're good at.";
        String responseString = head + "\r\n" + payLoad;
        return responseString;
    }

    @Override
    byte[] get() throws IOException {
        return produced.getBytes();
    }

    public void setHttpRequest(HTTPRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void produceContent() throws IOException {
        produced = produce405Response();
    }
}
