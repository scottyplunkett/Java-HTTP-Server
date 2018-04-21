package com.scottyplunkett.server.Cycle;

import com.scottyplunkett.server.Cycle.Response.Behavior.Behavior;
import com.scottyplunkett.server.Cycle.Request.HTTPRequest;
import com.scottyplunkett.server.Cycle.Response.HTTPResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class RequestResponseCycle implements Runnable {
    private final Path logPath = Paths.get("Logs/logs.html");
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private HTTPRequest request;
    private Behavior behavior;
    private HTTPResponse response;
    private byte[] message;


    public RequestResponseCycle(Socket connection, InputStream input, OutputStream output) throws IOException {
        socket = connection;
        in = input;
        out = output;
        request = new HTTPRequest(in);
        behavior = new Behavior(request);
        response = new HTTPResponse(request, behavior.handler);
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
