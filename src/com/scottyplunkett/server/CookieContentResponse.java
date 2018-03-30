package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class CookieContentResponse {
    private String cookieValue;
    private String cookieRequestHeaderValue;
    private Path cookieContentPath;
    private byte[] cookieResponseBodyContent;
    private byte[] cookieResponseHeaders;
    private byte[] response;

    CookieContentResponse(HTTPRequest request, String date) throws IOException {
        this(request, Paths.get("pages/cookie.html"), date);
    }

    CookieContentResponse(HTTPRequest request, Path path, String date) throws IOException {
        cookieContentPath = path;
        cookieValue = request.getRequestLine().split("\\s")[1].split("\\=")[1];
        cookieRequestHeaderValue = request.getCookie();
        cookieResponseBodyContent = Files.readAllBytes(cookieContentPath);
        cookieResponseHeaders =
                (new HTTPResponseHeaders("200", "text/html", date).get() + "Set-Cookie: " + cookieValue + "\r\n")
                        .getBytes();
        response = ByteArraysReducer.merge(cookieResponseBodyContent, cookieResponseHeaders);
    }



    public byte[] get() {
        return response;
    }
}


