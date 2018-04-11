package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;

class DefaultHandler extends Producer {
    HTTPRequest httpRequest;
    String date;
    String produced;

    DefaultHandler() {}

    DefaultHandler(HTTPRequest request, String _date) throws IOException {
        httpRequest = request;
        date = _date;
    }

    private String produceBasicResponse() throws IOException {
        String requestLine = httpRequest.getRequestLine();
        String requestedRoute = Parser.findRequestedRoute(requestLine);
        int encoded = HTTPResponseCode.encode(requestedRoute);
        String responseCode = HTTPResponseCode.retrieve(encoded);
        HTTPResponseHeaders headers = setHeaders(requestLine, date, responseCode);
        HTTPResponseBody body = new HTTPResponseBody(requestLine);
        return headers.get() + "\r\n" + body.get();
    }

    private HTTPResponseHeaders setHeaders(String requested, String date, String responseCode) throws IOException {
        String contentType = Files.probeContentType(Router.route(requested));
        if(requested.equals("OPTIONS /method_options HTTP/1.1" ))
            return new HTTPResponseHeaders(responseCode, contentType, date, "GET,HEAD,POST,OPTIONS,PUT");
        if(requested.equals("OPTIONS /method_options2 HTTP/1.1" ))
            return new HTTPResponseHeaders(responseCode, contentType, date, "GET,OPTIONS");
        else
            return new HTTPResponseHeaders(responseCode, contentType, date);
    }

    @Override
    byte[] get() throws IOException {
        return produced.getBytes();
    }

    @Override
    public void setHttpRequest(HTTPRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void produceContent() throws IOException {
        produced = produceBasicResponse();
    }
}
