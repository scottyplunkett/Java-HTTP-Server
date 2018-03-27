package com.scottyplunkett.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class HTTPServer {
    private ExecutorService executorService;
    private ServerSocket serverSocket;

    public static void main() throws IOException {
        main(new String[]{"5000"});
    }

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        new HTTPServer(new ServerSocket(port), Executors.newFixedThreadPool(10));
    }

    HTTPServer(ServerSocket serverSocket, ExecutorService executorService) throws IOException {
        while (!serverSocket.isClosed()) {
            Socket connection = serverSocket.accept();
            InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream();
            executorService.execute(new Cycler(connection, in, out));
        }
    }

    void close() throws IOException {
        executorService.shutdown();
        serverSocket.close();
    }
}