package com.scottyplunkett.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HTTPServerTest {
    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws IOException {
        HTTPServer httpServer = null;
    }

    @Test
    void testServes200ResponseToGetNicoleRequest(){
        String request = "GET /nicole HTTP/1.1";
        String response = HTTPServer.serve(request);
        int code = HTTPResponse.getCode(response);
        assertEquals(200, code);
    }
}