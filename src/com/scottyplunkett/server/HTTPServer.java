package com.scottyplunkett.server;

import com.scottyplunkett.server.Cycle.RequestResponseCycle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class HTTPServer {
    private static ProgramArguments programArguments;
    private ExecutorService pool;
    private ServerSocket internalConnection;

    public static void main(String[] args) throws IOException {
        programArguments = new ProgramArguments(args);
        ServerSocket serverSocket = new ServerSocket(programArguments.getPort());
        int processingCoresAvailable = Runtime.getRuntime().availableProcessors();
        new HTTPServer(serverSocket, Executors.newFixedThreadPool(processingCoresAvailable)).start();
    }

    HTTPServer(ServerSocket serverSocket, ExecutorService executorService) {
        internalConnection = serverSocket;
        pool = executorService;
    }

    void start() throws IOException {
        while (!internalConnection.isClosed()) {
            Socket connection = internalConnection.accept();
            InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream();
            pool.execute(new RequestResponseCycle(connection, in, out));
        }
    }

    void stop() throws IOException {
        pool.shutdown();
        internalConnection.close();
    }

}