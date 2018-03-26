package com.scottyplunkett.server;

import java.io.IOException;

class HTTPResponse {
    private HTTPRequest request;
    private String responseContent;

    HTTPResponse(HTTPRequest request) throws IOException {
        this(request, Date.getDate());
    }

    HTTPResponse(HTTPRequest request, String date) throws IOException {
        String requestLine = request.getRequestLine();
        if("/patch-content.txt".equals(requestLine.split("\\s")[1])) {
          PatchContentResponse patchContentResponse = new PatchContentResponse(request, date);
          responseContent = patchContentResponse.get();
        } else {
            String route = Parser.findRequestedRoute(requestLine);
            int encoded = HTTPResponseCode.encode(route);
            String responseCode = HTTPResponseCode.retrieve(encoded);
            HTTPResponseHeaders responseHeaders = setHeaders(requestLine, date, responseCode);
            HTTPResponseBody responseBody = new HTTPResponseBody(requestLine);
            responseContent = responseHeaders.get() + "\r\n" + responseBody.get();
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

    String get() {
        return responseContent;
    }

}