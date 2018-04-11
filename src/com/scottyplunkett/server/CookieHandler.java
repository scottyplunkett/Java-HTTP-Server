package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

class CookieHandler extends Producer {
    private final Path cookieContentPath = Paths.get("pages/cookie.html");
    private HTTPRequest httpRequest;
    private String date;
    private String cookieValue;
    private String cookieResponseBody;
    private String cookieResponseHeaders;
    private byte[] response;

    CookieHandler() {}

    CookieHandler(HTTPRequest request, String _date) throws IOException {
        httpRequest = request;
        date = _date;
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

    public void setHttpRequest(HTTPRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void produceContent() throws IOException {
        cookieValue = httpRequest.getCookie();
        cookieResponseBody = httpRequest.getRequestLine().contains("/eat_cookie") ?
                getEatCookieContent() : getCookieFileContent();
        String responseHeaders = new HTTPResponseHeaders("200 OK", "text/html", date).get();
        cookieResponseHeaders = responseHeaders + "Set-Cookie: " + cookieValue + "\r\n\r\n";
        response = ByteArraysReducer.merge(cookieResponseBody.getBytes(), cookieResponseHeaders.getBytes());
    }
}