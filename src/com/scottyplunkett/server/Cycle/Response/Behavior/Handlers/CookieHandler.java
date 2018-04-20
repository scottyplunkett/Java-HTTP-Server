package com.scottyplunkett.server.Cycle.Response.Behavior.Handlers;

import com.scottyplunkett.server.Cycle.Request.HTTPRequest;
import com.scottyplunkett.server.Cycle.Utils.ByteArraysReducer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class CookieHandler {
    private String cookieValue;
    private Path cookieContentPath;
    private String cookieResponseBody;
    private String cookieResponseHeaders;
    private byte[] response;

    public CookieHandler(HTTPRequest request, String date) throws IOException {
        this(request, Paths.get("pages/cookie.html"), date);
    }

    CookieHandler(HTTPRequest request, Path path, String date) throws IOException {
        cookieContentPath = path;
        cookieValue = request.getCookie();
        cookieResponseBody = request.getRequestLine().contains("/eat_cookie") ?
                             getEatCookieContent() : getCookieFileContent();
        String responseHeaders = new HTTPResponseHeaders("200 OK", "text/html", date).get();
        cookieResponseHeaders = responseHeaders + "Set-Cookie: " + cookieValue + "\r\n\r\n";
        response = ByteArraysReducer.merge(cookieResponseBody.getBytes(), cookieResponseHeaders.getBytes());
    }

    private String getCookieFileContent() throws IOException {
        return Files.lines(cookieContentPath).collect(Collectors.joining());
    }

    private String getEatCookieContent() {
        return "Eat mmmm " + cookieValue;
    }

    public byte[] get() {
        return response;
    }
}