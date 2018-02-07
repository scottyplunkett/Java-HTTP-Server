package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPResponseTest {

    class MockHTTPResponse extends HTTPResponse {

        public String send(String body) throws IOException {
            return body;
        }

        @Override
        public void send(BufferedWriter out, String body) throws IOException {
        }
    }

    @Test
    void testSendString() throws IOException {
        MockHTTPResponse httpResponse = new MockHTTPResponse();
        assertEquals("content", httpResponse.send("content"));
    }

    @Test
    void testSendFile() throws IOException {

    }
}