package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;

class HTTPResponse {
    private HTTPRequest httpRequest;
    private byte[] responseContent;
    private HTTPResponseHeaders headers;
    private HTTPResponseBody body;

    HTTPResponse(HTTPRequest request) throws IOException {
        this(request, Date.getDate());
    }

    HTTPResponse(HTTPRequest request, String date) throws IOException {
        httpRequest = request;
        String requestLine = httpRequest.getRequestLine();
        String route = Parser.findRequestedRoute(requestLine);
        int encoded = HTTPResponseCode.encode(route);
        String responseCode = HTTPResponseCode.retrieve(encoded);
        if(requestLine.contains("PATCH")) {
            responseContent = new PatchContentResponse(request, date).get().getBytes();
        } else if(requestLine.contains("image")) {
            responseContent = new ImageContentResponse(requestLine, date).get();
        } else if(requestLine.contains("cookie")) {
            responseContent = new CookieContentResponse(request, date).get();
        } else if(requestLine.contains("logs")) {
            responseContent = new LogContentResponse(request, date).get();
        } else if(requestLine.contains("form")) {
            responseContent = new FormContentResponse(request, date).get();
        } else if(requestLine.contains("partial_content")) {
            responseContent = new PartialContentResponse(request, date).get();
        } else {
            headers = setHeaders(requestLine, date, responseCode);
            body = new HTTPResponseBody(requestLine);
            String responseString = headers.get() + "\r\n" + body.get();
            responseContent = responseString.getBytes();
        }
    }

    private HTTPResponseHeaders setHeaders(String requested, String date, String responseCode) throws IOException {
        String contentType = Files.probeContentType(Router.route(requested));
        switch (requested) {
            case "OPTIONS /method_options HTTP/1.1" :
                return new HTTPResponseHeaders(responseCode, contentType, date, "GET,HEAD,POST,OPTIONS,PUT");
            case "OPTIONS /method_options2 HTTP/1.1" :
                return new HTTPResponseHeaders(responseCode, contentType, date, "GET,OPTIONS");
            default:
                return new HTTPResponseHeaders(responseCode, contentType, date);
        }
    }

    byte[] get() {
        return responseContent;
    }
}