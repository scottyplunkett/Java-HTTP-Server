//package com.scottyplunkett.server;
//
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class HTTPServerTest {
//    int port;
//    MockHTTPServer mock;
//
//    class MockHTTPServer extends HTTPServer {
//        ServerSocket testServerSocket;
//        ExecutorService testExecutorService;
//        boolean portAssigned = false;
//        boolean bound = false;
//        boolean openDuringExecution = true;
//        boolean closeStoppedExecutionAndClosedPort = false;
//
//        MockHTTPServer(ServerSocket serverSocket, ExecutorService executorService) throws IOException {
//            super(serverSocket, executorService);
//            testServerSocket = serverSocket;
//            testExecutorService = executorService;
//            this.portAssigned = testServerSocket.getLocalPort() == 5555;
//            this.bound = testServerSocket.isBound();
//            this.openDuringExecution = !testServerSocket.isClosed();
//        }
//    }
//
//    @Test
//    void assignsPort() throws IOException {
//        port = 5555;
//        mock = new MockHTTPServer(new ServerSocket(port), Executors.newFixedThreadPool(1));
//        mock.close();
//        mock.closeStoppedExecutionAndClosedPort = (mock.testServerSocket.isClosed() && mock.testExecutorService.isShutdown());
//        assertEquals(true, mock.portAssigned);
//    }
//
//    @Test
//    void listensForConnectionAfterBinding() throws IOException {
//        port = 5555;
//        mock = new MockHTTPServer(new ServerSocket(port), Executors.newFixedThreadPool(1));
//        this.mock.close();
//        mock.closeStoppedExecutionAndClosedPort = (mock.testServerSocket.isClosed() && mock.testExecutorService.isShutdown());
//        assertEquals(true, mock.bound);
//        assertEquals(true, mock.openDuringExecution);
//    }
//
//    @Test
//    void closeStopsExecutionAndClosesConnection() throws IOException {
//        port = 5555;
//        mock = new MockHTTPServer(new ServerSocket(port), Executors.newFixedThreadPool(1));
//        this.mock.close();
//        mock.closeStoppedExecutionAndClosedPort = (mock.testServerSocket.isClosed() && mock.testExecutorService.isShutdown());
//        assertEquals(true, mock.closeStoppedExecutionAndClosedPort);
//    }
//}