package com.scottyplunkett.server;

import java.io.IOException;

class HTTPResponse {
    private HTTPRequest httpRequest;
    private byte[] responseContent;

    HTTPResponse(HTTPRequest request) throws IOException {
        this(request, Date.getDate());
    }

    HTTPResponse(HTTPRequest request, String date) throws IOException {
        String requestLine = request.getRequestLine();
        String route = Parser.findRequestedRoute(requestLine);
        int encoded = HTTPResponseCode.encode(route);
        String responseCode = HTTPResponseCode.retrieve(encoded);
        if("/patch-content.txt".equals(requestLine.split("\\s")[1])) {
            PatchContentResponse patchContentResponse = new PatchContentResponse(request, date);
            responseContent = patchContentResponse.get().getBytes();
        } else {
            HTTPResponseHeaders responseHeaders = setHeaders(requestLine, date, responseCode);
            HTTPResponseBody responseBody = new HTTPResponseBody(requestLine);
            String responseString = responseHeaders.get() + "\r\n" + responseBody.get();
            responseContent = responseString.getBytes();
        }
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