package com.scottyplunkett.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class RequestResponseCycle implements Runnable {
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private HTTPRequest request;
    private HTTPResponse response;
    private byte[] message;

    RequestResponseCycle(Socket connection) throws IOException {
        this(connection, connection.getInputStream(), connection.getOutputStream());
    }

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
        out.write(message);
        out.flush();
        out.close();
        in.close();
        socket.close();
    }
}
