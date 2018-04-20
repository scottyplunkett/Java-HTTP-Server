package com.scottyplunkett.server;

import com.scottyplunkett.server.Cycle.Router;
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

    @Test
    void testRouteForGetRequestOfPatchContent() {
        Path expectedPath = Paths.get("public/patch-content.txt");
        assertEquals(expectedPath, Router.route("GET /patch-content.txt HTTP/1.1"));
    }

    @Test
    void testRouteForPatchRequestOfPatchContent() {
        Path expectedPath = Paths.get("public/patch-content.txt");
        assertEquals(expectedPath, Router.route("PATCH /patch-content.txt HTTP/1.1"));
    }

    @Test
    void testRouteForImageJPEG() {
        Path expectedPath = Paths.get("public/image.jpeg");
        assertEquals(expectedPath, Router.route("GET /image.jpeg HTTP/1.1"));
    }

    @Test
    void testRouteForImageGIF() {
        Path expectedPath = Paths.get("public/image.gif");
        assertEquals(expectedPath, Router.route("GET /image.gif HTTP/1.1"));
    }

    @Test
    void testRouteForImagePNG() {
        Path expectedPath = Paths.get("public/image.png");
        assertEquals(expectedPath, Router.route("GET /image.png HTTP/1.1"));
    }

    @Test
    void testRouteForTextFile() {
        Path expectedPath = Paths.get("public/text-file.txt");
        assertEquals(expectedPath, Router.route("GET /text-file.txt HTTP/1.1"));
    }

    @Test
    void testRouteForCookie() {
        Path expectedPath = Paths.get("pages/cookie.html");
        assertEquals(expectedPath, Router.route("GET /cookie?type=chocolate HTTP/1.1"));
    }
}