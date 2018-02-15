package com.scottyplunkett.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HTTPServerTest {

    @AfterEach
    void tearDown() throws IOException {
        HTTPServer httpServer = null;
    }

    @Test
    void testConnectToSpecificPort() throws IOException {
        int portNumber = 5555;
        HTTPServer httpServer = new HTTPServer();
        ServerSocket serverSocket = httpServer.connect(portNumber);
        assertEquals(5555, serverSocket.getLocalPort());
    }

    @Test
    void testClosePortClosesConnection() throws IOException {
        int portNumber = 5555;
        HTTPServer httpServer = new HTTPServer();
        ServerSocket serverSocket = httpServer.connect(portNumber);
        httpServer.close(serverSocket);
        assertEquals(true, serverSocket.isClosed());
    }
}