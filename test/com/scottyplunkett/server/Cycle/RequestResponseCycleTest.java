package com.scottyplunkett.server;

import com.scottyplunkett.server.Cycle.RequestResponseCycle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestResponseCycleTest {
    private MockCycle cycle;

    class MockCycle extends RequestResponseCycle {
        boolean loggedRequest;
        boolean messageSent;
        boolean runError;
        boolean completed;

        MockCycle(Socket connection, InputStream input, OutputStream output) throws IOException {
            super(connection, input, output);
        }

        @Override
        public void run() {
            try {
                invoke();
                messageSent = true;
                loggedRequest = true;
            } catch (IOException e) {
                e.printStackTrace();
                runError = true;
            }
            completed = true;
        }
    }

    @BeforeEach
    void setUp() throws IOException {
        ByteArrayOutputStream bytesSent = new ByteArrayOutputStream();
        ByteArrayInputStream bytesReceived = new ByteArrayInputStream("GET / HTTP/1.1".getBytes());
        cycle = new MockCycle(new Socket(), bytesReceived, bytesSent);
        cycle.run();
    }

    @AfterEach
    void tearDown() {
        cycle = null;
    }

    @Test
    void invocationSendsMessageSuccessfully() {
        assertEquals(true, cycle.messageSent);
    }

    @Test
    void logsRequest() {
        assertEquals(true, cycle.loggedRequest);
    }

    @Test
    void noErrors() {
        assertEquals(false, cycle.runError);
    }

    @Test
    void completesRun() {
        assertEquals(true, cycle.completed);
    }
}