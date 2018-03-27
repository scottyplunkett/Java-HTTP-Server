package com.scottyplunkett.server;

import java.io.*;
import java.net.Socket;

class Cycler implements Runnable {
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private HTTPRequest request;
    private HTTPResponse response;
    private byte[] packet;

    Cycler(Socket connection, InputStream input, OutputStream output) throws IOException {
        socket = connection;
        in = input;
        out = output;
        request = new HTTPRequest(in);
        response = new HTTPResponse(request);
        packet = response.get();
    }

    private void invoke() throws IOException {
        out.write(packet);
        out.flush();
        System.err.println("Client served…");
        out.close();
        in.close();
        socket.close();
        System.err.println("Connection Terminated!");
    }

    @Override
    public void run() {
        try {
            invoke();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
