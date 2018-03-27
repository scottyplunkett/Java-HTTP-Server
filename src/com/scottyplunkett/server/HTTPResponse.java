package com.scottyplunkett.server;

import java.io.IOException;

class HTTPResponse {
    private byte[] responseContent;

    HTTPResponse(HTTPRequest request) throws IOException {
        this(request.getRequestLine(), Date.getDate());
    }

    HTTPResponse(String requested) throws IOException {
        this(requested, Date.getDate());
    }

    HTTPResponse(String requested, String date) throws IOException {
        String route = Parser.findRequestedRoute(requested);
        int encoded = HTTPResponseCode.encode(route);
        String responseCode = HTTPResponseCode.retrieve(encoded);
        HTTPResponseHeaders responseHeaders = setHeaders(requested, date, responseCode);
        HTTPResponseBody responseBody = new HTTPResponseBody(requested);
        String responseString = responseHeaders.get() + "\r\n" + responseBody.get();
        responseContent = responseString.getBytes();
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

    byte[] get() {
        return responseContent;
    }
}