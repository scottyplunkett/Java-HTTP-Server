package com.scottyplunkett.server.cycle.response.behavior;

import com.scottyplunkett.server.cycle.request.HTTPRequest;
import com.scottyplunkett.server.cycle.response.behavior.handlers.Handler;
import com.scottyplunkett.server.cycle.utils.Parser;

public class Behavior {
    public Handler handler;
    HTTPRequest httpRequest;

    public Behavior(HTTPRequest _httpRequest) {
        httpRequest = _httpRequest;
        handler = deduceFromRequestLine(httpRequest.getRequestLine())
                                                   .getHandler();
    }

    private SetHandler deduceFromRequestLine(String requestLine) {
        if (isRestricted(requestLine)) return SetHandler.RESTRICTEDMETHOD;
        if (requestLine.contains("patch")) return SetHandler.PATCH;
        if (requestLine.contains("image")) return SetHandler.IMAGE;
        if (requestLine.contains("cookie")) return SetHandler.COOKIE;
        if (requestLine.contains("logs")) return SetHandler.LOG;
        if (requestLine.contains("form")) return SetHandler.FORM;
        if (requestLine.contains("partial_content")) return SetHandler.PARTIAL;
        else return SetHandler.DEFAULT;
    }

    private boolean isRestricted(String requestLine) {
        return isMethodRestrictedOnRoute(Parser.findRequestedRoute(requestLine),
                                         Parser.findRequestMethod(requestLine));
    }

    private boolean isMethodRestrictedOnRoute(String requestedRoute, String method) {
        return ((requestedRoute.equals("/file1") || requestedRoute.equals("/text-file.txt"))
                && !method.equals("GET"));
    }
}