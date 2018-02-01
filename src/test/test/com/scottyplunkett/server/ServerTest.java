package com.scottyplunkett.server;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class ServerTest {
    public Server server;
    private String path;

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws IOException {
        this.server = null;
    }


    @Test
    void testRouteGetRoot() throws IOException, URISyntaxException {
        requestRoute("GET / HTTP/1.1");
        Assertions.assertEquals("/helloworld.html", this.path);

    }
    @Test
    void testRouteGetNicole() throws IOException, URISyntaxException {
        requestRoute("GET /nicole HTTP/1.1");
        Assertions.assertEquals("/nicole.html", this.path);

    }

    @Test
    void testRouteGetPaul() throws IOException, URISyntaxException {
        requestRoute("GET /paul HTTP/1.1");
        Assertions.assertEquals("/paul.html", this.path);
    }

    @Test
    void testRouteGetJosh() throws IOException, URISyntaxException {
        requestRoute("GET /josh HTTP/1.1");
        Assertions.assertEquals("/josh.html", this.path);
    }



    private void requestRoute(String s) throws URISyntaxException {
        this.path = Server.route(s);
    }

}