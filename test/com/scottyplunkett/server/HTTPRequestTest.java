package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPRequestTest {

    @Test
    void getRequestLine() throws IOException {
        InputStream stream = new ByteArrayInputStream("requestLine\r\nline2\r\nline3\r\nline4".getBytes());
        InputStreamReader feed = new InputStreamReader(stream);
        BufferedReader in = new BufferedReader(feed);
        HTTPRequest request = new HTTPRequest(in);
        assertEquals("requestLine", request.getRequestLine());
    }

    @Test
    void getEtag() throws IOException {
        String inputRequestString = "PATCH /patch-content.txt HTTP/1.1\r\n" +
                                     "If-Match: 5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0\r\n" +
                                     "Content-Length: 15\r\n";
        InputStream inputStream = new ByteArrayInputStream(inputRequestString.getBytes());
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader in = new BufferedReader(streamReader);
        HTTPRequest request = new HTTPRequest(in);
        assertEquals("5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0", request.getEtag());
    }

    @Test
    void getEtagWithIfNoneMatch() throws IOException {
        String inputRequestString = "PATCH /patch-content.txt HTTP/1.1\r\n" +
                                    "If-None-Match: 5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0\r\n" +
                                    "Content-Length: 15\r\n";
        InputStream inputStream = new ByteArrayInputStream(inputRequestString.getBytes());
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader in = new BufferedReader(streamReader);
        HTTPRequest request = new HTTPRequest(in);
        assertEquals("5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0", request.getEtag());
    }
}