package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPRequestTest {

    @Test
    void getRequestLine() throws IOException {
        InputStream stream = new ByteArrayInputStream("requestLine\r\nline2\r\nline3\r\nline4".getBytes());
        HTTPRequest request = new HTTPRequest(stream);
        assertEquals("requestLine", request.getRequestLine());
    }

    @Test
    void getCookie() throws IOException {
        InputStream stream = new ByteArrayInputStream("requestLine\r\nCookie: chocolate\r\nline3\r\nline4".getBytes());
        HTTPRequest request = new HTTPRequest(stream);
        assertEquals("chocolate", request.getCookie());
    }

    @Test
    void getCookieWhenSetInParams() throws IOException {
        InputStream stream = new ByteArrayInputStream("GET /cookie?type=chocolate\r\nline2\r\nline3\r\nline4".getBytes());
        HTTPRequest request = new HTTPRequest(stream);
        assertEquals("chocolate", request.getCookie());
    }

    @Test
    void getAuthorization() throws IOException {
        InputStream stream = new ByteArrayInputStream("GET /logs HTTP/1.1\r\nAuthorization: Basic YWRtaW46aHVudGVyMg==\r\nline3\r\nline4".getBytes());
        HTTPRequest request = new HTTPRequest(stream);
        assertEquals("Basic YWRtaW46aHVudGVyMg==", request.getAuthorization());
    }
}