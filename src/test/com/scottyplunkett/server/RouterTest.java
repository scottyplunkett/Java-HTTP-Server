package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouterTest {

    @Test
    void testRouteGetRoot() throws IOException, URISyntaxException {
        File file = new File("/helloworld.html");
        Router router = new Router();
        assertEquals( file, router.route("GET / HTTP/1.1"));
    }

    @Test
    void testRouteGetNicole() throws IOException, URISyntaxException {
        File file = new File("/nicole.html");
        Router router = new Router();
        assertEquals(file, router.route("GET /nicole HTTP/1.1"));
    }

    @Test
    void testRouteGetPaul() throws IOException, URISyntaxException {
        File file = new File("/paul.html");
        Router router = new Router();
        assertEquals(file, router.route("GET /paul HTTP/1.1"));
    }

    @Test
    void testRouteGetJosh() throws IOException, URISyntaxException {
        File file = new File("/josh.html");
        Router router = new Router();
        assertEquals(file, router.route("GET /josh HTTP/1.1"));
    }

    @Test
    void testRouteGetInvalidResource() throws IOException, URISyntaxException {
        File file = new File("/404.html");
        Router router = new Router();
        assertEquals(file, router.route("GET /turtle HTTP/1.1"));
    }

}