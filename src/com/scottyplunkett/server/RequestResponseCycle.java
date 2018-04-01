package com.scottyplunkett.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

class RequestResponseCycle implements Runnable {
    private final Path logPath = Paths.get("Logs/logs.html");

    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private HTTPRequest request;
    private HTTPResponse response;
    private byte[] message;

    RequestResponseCycle(Socket connection, InputStream input, OutputStream output) throws IOException {
        socket = connection;
        in = input;
        out = output;
        request = new HTTPRequest(in);
        response = new HTTPResponse(request);
        message = response.get();
    }

    @Override
    public void run() {
        try {
            invoke();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void invoke() throws IOException {
        logRequest();
        out.write(message);
        out.flush();
        out.close();
        in.close();
        socket.close();
    }

    private void logRequest() throws IOException {
        String time = "<h1><b>" + String.valueOf(System.nanoTime()) + " : </b>";
        String logLine =  time + request.getRequestLine() + "</h1><br>\r\n";
        Files.write(logPath, logLine.getBytes(), StandardOpenOption.APPEND);
    }
}
