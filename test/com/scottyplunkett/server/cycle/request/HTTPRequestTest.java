package com.scottyplunkett.server.cycle.request;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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
        String requestWithAuth = "GET /logs HTTP/1.1\r\nAuthorization: Basic YWRtaW46aHVudGVyMg==\r\nline3\r\nline4";
        InputStream stream = new ByteArrayInputStream(requestWithAuth.getBytes());
        HTTPRequest request = new HTTPRequest(stream);
        assertEquals("Basic YWRtaW46aHVudGVyMg==", request.getAuthorization());
    }

    @Test
    void getRange() throws IOException {
        String requestWithRange = "GET /partial_content.txt HTTP/1.1\r\nRange: bytes=0-4\r\nline3\r\n\r\n";
        InputStream stream = new ByteArrayInputStream(requestWithRange.getBytes());
        HTTPRequest request = new HTTPRequest(stream);
        String expectedRange = "0-4";
        assertEquals(expectedRange, request.getRange());
    }

    @Test
    void getRangeWhenOnlyRangeEndPresent() throws IOException {
        String requestWithRange = "GET /partial_content.txt HTTP/1.1\r\nRange: bytes=-6\r\nline3\r\n\r\n";
        InputStream stream = new ByteArrayInputStream(requestWithRange.getBytes());
        HTTPRequest request = new HTTPRequest(stream);
        String expectedRange = "-6";
        assertEquals(expectedRange, request.getRange());
    }

    @Test
    void getRangeWhenOnlyRangeStartPresent() throws IOException {
        String requestWithRange = "GET /partial_content.txt HTTP/1.1\r\nRange: bytes=4-\r\nline3\r\n\r\n";
        InputStream stream = new ByteArrayInputStream(requestWithRange.getBytes());
        HTTPRequest request = new HTTPRequest(stream);
        String expectedRange = "4-";
        assertEquals(expectedRange, request.getRange());
    }
}