package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPResponseCodeTest {

    @Test
    void retrieve() {
        assertEquals("302 Found", HTTPResponseCode.retrieve(302));
        assertEquals("200 OK", HTTPResponseCode.retrieve(200));
        assertEquals("206 Partial Content", HTTPResponseCode.retrieve(206));
        assertEquals("204 No Content", HTTPResponseCode.retrieve(204));
        assertEquals("302 Found", HTTPResponseCode.retrieve(302));
        assertEquals("400 Bad Request", HTTPResponseCode.retrieve(400));
        assertEquals("401 Unauthorized", HTTPResponseCode.retrieve(401));
        assertEquals("404 Not Found", HTTPResponseCode.retrieve(404));
        assertEquals("405 Method Not Allowed", HTTPResponseCode.retrieve(405));
        assertEquals("416 Range Not Satisfiable", HTTPResponseCode.retrieve(416));
        assertEquals("409 Conflict", HTTPResponseCode.retrieve(409));
        assertEquals("418 I'm a teapot", HTTPResponseCode.retrieve(418));
        assertEquals("500 Internal Server Error", HTTPResponseCode.retrieve(500));
    }

    @Test
    void encode() {
        assertEquals(302, HTTPResponseCode.encode("/redirect"));
        assertEquals(200, HTTPResponseCode.encode("/"));
        assertEquals(404, HTTPResponseCode.encode("/foobar"));
    }

}