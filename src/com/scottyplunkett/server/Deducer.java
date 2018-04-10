package com.scottyplunkett.server;

public class Deducer {

    Deducer() {}

    Producer deduceProducer(String requestLine) {
        String method = Parser.findRequestMethod(requestLine);
        if (requestLine.contains("patch")) return new PatchContentResponse();
        if (requestLine.contains("image")) return new ImageContentResponse();
        if (requestLine.contains("cookie")) return new CookieContentResponse();
        if (requestLine.contains("logs")) return new LogContentResponse();
        if (requestLine.contains("form")) return new FormContentResponse();
        if (requestLine.contains("partial_content")) return new PartialContentResponse();
        return isMethodRestrictedOnRoute(Parser.findRequestedRoute(requestLine), method) ?
                new MethodNotAllowedContentResponse() :
                new BasicContentResponse();
    }

    private boolean isMethodRestrictedOnRoute(String requestedRoute, String method) {
        return ((requestedRoute.equals("/file1") || requestedRoute.equals("/text-file.txt"))
                && !method.equals("GET"));
    }
}