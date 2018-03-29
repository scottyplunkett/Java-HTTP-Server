package com.scottyplunkett.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class MockServerSocket extends ServerSocket {
    Socket socket;

    MockServerSocket(int port) throws IOException {
        super(port);
    }

    @Override
    public Socket accept() {
        Object socketMock = socket;
        return (Socket) socketMock;
    }
}
