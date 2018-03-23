package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouterTest {

    @Test
    void testRouteGetRoot() {
        Path expectedPath = Paths.get("public");
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

    @Test
    void testRouteGetFile() {
        Path expectedPath = Paths.get("pages/file1");
        assertEquals(expectedPath, Router.route("GET /file1 HTTP/1.1"));
    }

    @Test
    void testRouteGetCoffee() {
        Path expectedPath = Paths.get("pages/418.html");
        assertEquals(expectedPath, Router.route("GET /coffee HTTP/1.1"));
    }

    @Test
    void testRouteForRequestWithParamsDynamicRoute() {
        Path expectedPath = Paths.get("pages/dynamic.html");
        assertEquals(expectedPath, Router.route("GET /parameters?variable_1=bla&variable_2=bla HTTP/1.1"));
    }

    @Test
    void testRouteForRequestOfMethodOptions() {
        Path expectedPath = Paths.get("pages/method_options.html");
        assertEquals(expectedPath, Router.route("OPTIONS /method_options HTTP/1.1"));
    }

    @Test
    void testRouteForRequestOfMethodsOptions2() {
        Path expectedPath = Paths.get("pages/method_options.html");
        assertEquals(expectedPath, Router.route("OPTIONS /method_options2 HTTP/1.1"));
    }
}