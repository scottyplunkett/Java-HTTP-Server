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
        String requestedRoute = Parser.findRequestedRoute(requestLine);
        String method = Parser.findRequestMethod(requestLine);
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
            if((requestedRoute.equals("/file1") ||
               requestedRoute.equals("/text-file.txt")) &&
               method.equals("GET") == false) responseContent = generate405Response(date, requestLine).getBytes();
            else {
                int encoded = HTTPResponseCode.encode(requestedRoute);
                String responseCode = HTTPResponseCode.retrieve(encoded);
                headers = setHeaders(requestLine, date, responseCode);
                body = new HTTPResponseBody(requestLine);
                String responseString = headers.get() + "\r\n" + body.get();
                responseContent = responseString.getBytes();
            }
        }
    }

    private String generate405Response(String date, String requestLine) throws IOException {
        String head = setHeaders(requestLine, date, "405 Method Not Allowed").get();
        head = head + "Allow: GET\r\n";
        String payLoad = "405: Method Not Allowed... Stick to what you're good at.";
        String responseString = head + "\r\n" + payLoad;
        return responseString;
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