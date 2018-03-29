package com.scottyplunkett.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPServerTest {
    MockHTTPServer mock;

    @BeforeEach
    void setUp() throws IOException {
        mock = new MockHTTPServer(new MockServerSocket(5555), Executors.newSingleThreadExecutor());
    }

    @AfterEach
    void tearDown() {
        mock = null;
    }

    @Test
    void assignsPort() {
        assertEquals(5555, mock.mockServerSocket.getLocalPort());
    }

    @Test
    void listensForConnectionUntilStop() {
        assertEquals(true, mock.listenedForConnections);
    }

    @Test
    void callsExecuteOnRunnable() {
        assertEquals(true, mock.executed);
    }

    @Test
    void stopClosesConnection() {
        assertEquals(true, mock.stopClosedInternalConnection);
    }

    @Test
    void stopEndsExecution() {
        assertEquals(true, mock.stopShutdownExecutorService);
    }
}