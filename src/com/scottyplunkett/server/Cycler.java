package com.scottyplunkett.server;

import java.io.*;
import java.net.Socket;

class Cycler implements Runnable {
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    Cycler(Socket socket, InputStream in, OutputStream out) throws IOException {
        this.socket = socket;
        this.in = in;
        this.out = out;
    }

    private void invoke() throws IOException {
        HTTPRequest request = new HTTPRequest(in);
        byte[] response = new HTTPResponse(request).get();
        out.write(response);
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
