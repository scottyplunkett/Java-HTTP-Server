package com.scottyplunkett.server;

import java.io.IOException;

class HTTPResponse {
    private Producer producer;
    private String date;
    private HTTPRequest httpRequest;
    private byte[] responseContent;
    private HTTPResponseHeaders headers;
    private HTTPResponseBody body;

    HTTPResponse(HTTPRequest request) throws IOException {
        this(request, Date.getDate());
    }

    HTTPResponse(HTTPRequest request, String _date) throws IOException {
        httpRequest = request;
        date = _date;
        producer = deduceProducer(request.getRequestLine());
        producer.setHttpRequest(httpRequest);
        producer.setDate(date);
        produceContent();
    }

    private Producer deduceProducer(String deduced) throws IOException {
        String method = Parser.findRequestMethod(deduced);
        if (deduced.contains("patch")) return new PatchContentResponse();
        if (deduced.contains("image")) return new ImageContentResponse();
        if (deduced.contains("cookie")) return new CookieContentResponse();
        if (deduced.contains("logs")) return new LogContentResponse();
        if (deduced.contains("form")) return new FormContentResponse();
        if (deduced.contains("partial_content")) return new PartialContentResponse();
        if (isMethodRestrictedOnRoute(deduced, method)) return new MethodNotAllowedContentResponse();
        else return new BasicContentResponse();
    }

    private void produceContent() throws IOException {
        producer.produceContent();
    }



    private boolean isMethodRestrictedOnRoute(String requestedRoute, String method) {
        return (requestedRoute.equals("/file1") || requestedRoute.equals("/text-file.txt"))
                && method.equals("GET") == false;
    }



    byte[] get() throws IOException {
        return producer.get();
    }
}