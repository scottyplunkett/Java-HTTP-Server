package com.scottyplunkett.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ServerConnection extends Thread {
    Socket socket;
    DataInputStream dataIn;
    DataOutputStream dataOut;

    ServerConnection(Socket socket) {
        super("ServerConnectionThread");
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            createStreams();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createStreams() throws IOException {
        dataIn = new DataInputStream(socket.getInputStream());
        dataOut = new DataOutputStream(socket.getOutputStream());
    }
}
