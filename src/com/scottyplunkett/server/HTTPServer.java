package com.scottyplunkett.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class HTTPServer {
    private ExecutorService pool;
    private ServerSocket internalConnection;

    public static void main() throws IOException {
        main(new String[]{"5000"});
    }

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        new HTTPServer(new ServerSocket(port), Executors.newFixedThreadPool(10)).start();;
    }

    HTTPServer(ServerSocket serverSocket, ExecutorService executorService) throws IOException {
        internalConnection = serverSocket;
        pool = executorService;
    }

    void start() throws IOException {
        while (!internalConnection.isClosed()) {
            pool.execute(new RequestResponseCycle(internalConnection.accept()));
        }
    }

    void stop() throws IOException {
        pool.shutdown();
        internalConnection.close();
    }
}