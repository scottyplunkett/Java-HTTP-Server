package com.scottyplunkett.server;

import java.io.IOException;

class HTTPResponse {
    private String responseContent;

    HTTPResponse(String requested) throws IOException {
        this(requested, Date.getDate());
    }

    HTTPResponse(String requested, String date) throws IOException {
        String route = Parser.findRequestedRoute(requested);
        int encoded = HTTPResponseCode.encode(route);
        String responseCode = HTTPResponseCode.retrieve(encoded);
        String responseHeaders = new HTTPResponseHeaders(responseCode, "text/html", date).get();
        HTTPResponseBody responseBody = new HTTPResponseBody(requested);
        responseContent = responseHeaders + "\r\n" + responseBody.get();
    }

    String get() {
        return responseContent;
    }
}