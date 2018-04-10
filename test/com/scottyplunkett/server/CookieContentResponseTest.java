package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.scottyplunkett.server.ByteArraysReducer.merge;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CookieContentResponseTest {

    @Test
    void get() throws IOException {
        String requestCookieWithChocolate = "GET /cookie?type=chocolate HTTP/1.1\r\nline 4\r\nline3\r\n";
        InputStream in = new ByteArrayInputStream(requestCookieWithChocolate.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] body = "Eat".getBytes();
        HTTPResponseHeaders headersBeforeAppend = new HTTPResponseHeaders("200 OK", "text/html", "bla");
        byte[] head = (headersBeforeAppend.get() + "Set-Cookie: chocolate\r\n" + "\r\n").getBytes();
        byte[] expectedResponse = merge(body, head);
        CookieContentResponse cookieContentResponse = new CookieContentResponse(request, "bla");
        cookieContentResponse.produceContent();
        assertArrayEquals(expectedResponse, cookieContentResponse.get());
    }

    @Test
    void getEatCookie() throws IOException {
        String requestToEatCookie = "GET /eat_cookie\r\nCookie: chocolate\r\nline3\r\n";
        InputStream in = new ByteArrayInputStream(requestToEatCookie.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] body = "Eat mmmm chocolate".getBytes();
        HTTPResponseHeaders headersBeforeAppend = new HTTPResponseHeaders("200 OK", "text/html", "bla");
        byte[] head = (headersBeforeAppend.get() + "Set-Cookie: chocolate\r\n" + "\r\n").getBytes();
        byte[] expectedResponse = merge(body, head);
        CookieContentResponse cookieContentResponse = new CookieContentResponse(request, "bla");
        cookieContentResponse.produceContent();
        assertArrayEquals(expectedResponse, cookieContentResponse.get());
    }
}