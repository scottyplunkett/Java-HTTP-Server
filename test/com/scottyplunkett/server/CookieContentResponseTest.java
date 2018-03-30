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
        byte[] head = (new HTTPResponseHeaders("200", "text/html", "bla").get() + "Set-Cookie: chocolate" + "\r\n").getBytes();
        byte[] expectedResponse = merge(body, head);
        assertArrayEquals(expectedResponse, new CookieContentResponse(request, "bla").get());
    }
//
//    @Test
//    void getWithCookie() {
//
//    }

}