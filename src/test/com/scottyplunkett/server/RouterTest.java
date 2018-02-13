package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouterTest {

    @Test
    void testRouteGetRoot() {
        Path expectedPath = Paths.get("pages/helloworld.html");
        assertEquals( expectedPath, Router.route("GET / HTTP/1.1"));
    }

    @Test
    void testRouteGetNicole() {
        Path expectedPath = Paths.get("pages/nicole.html");
        assertEquals(expectedPath, Router.route("GET /nicole HTTP/1.1"));

    }

    @Test
    void testRouteGetPaul() {
        Path expectedPath = Paths.get("pages/paul.html");
        assertEquals(expectedPath, Router.route("GET /paul HTTP/1.1"));
    }

    @Test
    void testRouteGetJosh() {
        Path expectedPath = Paths.get("pages/josh.html");
        assertEquals(expectedPath, Router.route("GET /josh HTTP/1.1"));
    }

    @Test
    void testRouteGetInvalidResource() {
        Path expectedPath = Paths.get("pages/404.html");
        assertEquals(expectedPath, Router.route("GET /turtle HTTP/1.1"));
    }

}