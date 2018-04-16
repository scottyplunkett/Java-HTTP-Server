package com.scottyplunkett.server.Cycle.Behavior;

import com.scottyplunkett.server.Cycle.Behavior.handlers.*;
import com.scottyplunkett.server.Cycle.Request.HTTPRequest;
import com.scottyplunkett.server.Cycle.Utils.Parser;

public class Behavior {
    public Handler handler;
    HTTPRequest httpRequest;

    public Behavior(HTTPRequest _httpRequest) {
        httpRequest = _httpRequest;
        handler = deduceFromRequestLine(httpRequest.getRequestLine());
    }

    private Handler deduceFromRequestLine(String requestLine) {
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