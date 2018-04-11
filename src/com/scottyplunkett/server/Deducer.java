package com.scottyplunkett.server;

public class Deducer {

    Deducer() {}

    Producer deduceProducer(String requestLine) {
        String method = Parser.findRequestMethod(requestLine);
        if (requestLine.contains("patch")) return new PatchHandler();
        if (requestLine.contains("image")) return new ImageHandler();
        if (requestLine.contains("cookie")) return new CookieHandler();
        if (requestLine.contains("logs")) return new LogsHandler();
        if (requestLine.contains("form")) return new FormHandler();
        if (requestLine.contains("partial_content")) return new PartialHandler();
        return isMethodRestrictedOnRoute(Parser.findRequestedRoute(requestLine), method) ?
                new RestrictedMethodHandler() :
                new DefaultHandler();
    }

    private boolean isMethodRestrictedOnRoute(String requestedRoute, String method) {
        return ((requestedRoute.equals("/file1") || requestedRoute.equals("/text-file.txt"))
                && !method.equals("GET"));
    }
}