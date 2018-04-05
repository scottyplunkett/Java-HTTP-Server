package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;

class HTTPResponse {
    private HTTPRequest request;
    private String requestLine;
    private String requestedRoute;
    private String requestMethod;
    private String date;
    private byte[] responseContent;
    private HTTPResponseHeaders headers;
    private HTTPResponseBody body;

    HTTPResponse(HTTPRequest request) throws IOException {
        this(request, Date.getDate());
    }

    HTTPResponse(HTTPRequest httpRequest, String dateSet) throws IOException {
        request = httpRequest;
        date = dateSet;
        requestLine = httpRequest.getRequestLine();
        requestedRoute = Parser.findRequestedRoute(requestLine);
        requestMethod = Parser.findRequestMethod(requestLine);
        responseContent = generateResponseContent();
    }

    private byte[] generateResponseContent() throws IOException {
        if(requestLine.contains("patch")) return new PatchContentResponse(request, date).get().getBytes();
        if(requestLine.contains("image")) return new ImageContentResponse(requestLine, date).get();
        if(requestLine.contains("cookie")) return new CookieContentResponse(request, date).get();
        if(requestLine.contains("logs")) return new LogContentResponse(request, date).get();
        if(requestLine.contains("form")) return new FormContentResponse(request, date).get();
        if(requestLine.contains("partial")) return new PartialContentResponse(request, date).get();
        else return generateOtherResponseType();
    }

    private byte[] generateOtherResponseType() throws IOException {
        if((requestedRoute.equals("/file1") ||
           requestedRoute.equals("/text-file.txt")) &&
           requestMethod.equals("GET") == false) return generate405Response().getBytes();
        else {
            int encoded = HTTPResponseCode.encode(requestedRoute);
            String responseCode = HTTPResponseCode.retrieve(encoded);
            headers = setHeaders(responseCode);
            body = new HTTPResponseBody(requestLine);
            String responseString = headers.get() + "\r\n" + body.get();
            return responseString.getBytes();
        }
    }

    private String generate405Response() throws IOException {
        String head = setHeaders("405 Method Not Allowed").get();
        head = head + "Allow: GET\r\n";
        String payLoad = "405: Method Not Allowed... Stick to what you're good at.";
        String responseString = head + "\r\n" + payLoad;
        return responseString;
    }

    private HTTPResponseHeaders setHeaders(String responseCode) throws IOException {
        String contentType = Files.probeContentType(Router.route(requestLine));
        switch (requestLine) {
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