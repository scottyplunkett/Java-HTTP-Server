package com.scottyplunkett.server;

import java.io.IOException;

class HTTPResponse {
    private String responseContent;
    private HTTPRequest request;


    HTTPResponse(String requested) throws IOException {
        this(requested, Date.getDate());
    }

    HTTPResponse(String requested, String date) throws IOException {
        String route = Parser.findRequestedRoute(requested);
        int encoded = HTTPResponseCode.encode(route);
        String responseCode = HTTPResponseCode.retrieve(encoded);
        HTTPResponseHeaders responseHeaders = setHeaders(requested, date, responseCode);
        HTTPResponseBody responseBody = new HTTPResponseBody(requested);
        responseContent = responseHeaders.get() + "\r\n" + responseBody.get();
    }

    private HTTPResponseHeaders setHeaders(String requested, String date, String responseCode) {
        switch (requested) {
            case "OPTIONS /method_options HTTP/1.1" :
                return new HTTPResponseHeaders(responseCode, "text/html", date, "GET,HEAD,POST,OPTIONS,PUT");
            case "OPTIONS /method_options2 HTTP/1.1" :
                return new HTTPResponseHeaders(responseCode, "text/html", date, "GET,OPTIONS");
            default:
                return new HTTPResponseHeaders(responseCode, "text/html", date);
        }
    }

    String get() {
        return responseContent;
    }
}