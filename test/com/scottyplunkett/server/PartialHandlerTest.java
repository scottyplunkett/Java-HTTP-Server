package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static com.scottyplunkett.server.ByteArraysReducer.merge;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class PartialHandlerTest {
    @Test
    void getRangeOfPartialContent() throws IOException {
        String requestPartialContent = "GET /partial_content.txt HTTP/1.1\r\nRange: bytes=0-4\r\nline3\r\n\r\n";
        InputStream in = new ByteArrayInputStream(requestPartialContent.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        String headers = new HTTPResponseHeaders("206 Partial Content", "text/plain", "bla").get();
        headers = headers + "Content-Range: bytes 0-5/77\r\n";
        headers = headers + "Content-Length: 5\r\n";
        byte[] head = (headers + "\r\n").getBytes();
        byte[] partialContent = "This is a file that contains text to read part of in order to fulfill a 206.\n".getBytes();
        byte[] body = Arrays.copyOfRange(partialContent,0,5);
        byte[] expectedResponse = merge(body, head);
        assertArrayEquals(expectedResponse, new PartialHandler(request, "bla").get());
    }

    @Test
    void getRangeOfPartialContentWhenOnlyRangeEndPresent() throws IOException {
        String requestPartialContentWithOnlyRangeEnd = "GET /partial_content.txt HTTP/1.1\r\nRange: bytes=-6\r\nline3\r\n\r\n";
        InputStream in = new ByteArrayInputStream(requestPartialContentWithOnlyRangeEnd.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        String headers = new HTTPResponseHeaders("206 Partial Content", "text/plain", "bla").get();
        headers = headers + "Content-Range: bytes 71-77/77\r\n";
        headers = headers + "Content-Length: 6\r\n";
        byte[] head = (headers + "\r\n").getBytes();
        byte[] partialContent = "This is a file that contains text to read part of in order to fulfill a 206.\n".getBytes();
        byte[] body = Arrays.copyOfRange(partialContent,71,77);
        byte[] expectedResponse = merge(body, head);
        assertArrayEquals(expectedResponse, new PartialHandler(request, "bla").get());
    }

}