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
}