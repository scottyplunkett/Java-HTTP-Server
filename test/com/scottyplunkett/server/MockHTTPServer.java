package com.scottyplunkett.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

class MockHTTPServer extends HTTPServer {
    ServerSocket mockServerSocket;
    ExecutorService mockExecutorService;
    boolean listenedForConnections;
    boolean executed;
    boolean stopShutdownExecutorService;
    boolean stopClosedInternalConnection;

    MockHTTPServer(ServerSocket serverSocket, ExecutorService executorService) throws IOException {
        super(serverSocket, executorService);
        mockServerSocket = serverSocket;
        mockExecutorService = executorService;
        start();
        checkForProperShutdown();
    }

    @Override
    void start() throws IOException {
        while (!mockServerSocket.isClosed()) {
            mockServerSocket.accept();
            listenedForConnections = true;
            mockExecutorService.execute(() -> executed = true);
            stop();
        }
    }

    void checkForProperShutdown() {
        if (mockExecutorService.isShutdown()) stopShutdownExecutorService = true;
        if (mockServerSocket.isClosed()) stopClosedInternalConnection = true;
    }
}
