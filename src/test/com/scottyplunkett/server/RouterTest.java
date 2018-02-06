package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouterTest {
    private String path;

    @Test
    void testRouteGetRoot() throws IOException, URISyntaxException {
        Router router = new Router();
        assertEquals("/helloworld.html",
                   router.route("GET / HTTP/1.1"));
    }

    @Test
    void testRouteGetNicole() throws IOException, URISyntaxException {
        Router router = new Router();
        assertEquals("/nicole.html",
                   router.route("GET /nicole HTTP/1.1"));

    }

    @Test
    void testRouteGetPaul() throws IOException, URISyntaxException {
        Router router = new Router();
        assertEquals("/paul.html",
                   router.route("GET /paul HTTP/1.1"));
    }

    @Test
    void testRouteGetJosh() throws IOException, URISyntaxException {
        Router router = new Router();
        assertEquals("/josh.html",
                router.route("GET /josh HTTP/1.1"));
    }

    @Test
    void testRouteGetInvalidResource() throws IOException, URISyntaxException {
        Router router = new Router();
        assertEquals("/404.html",
                router.route("GET /turtle HTTP/1.1"));
    }

}