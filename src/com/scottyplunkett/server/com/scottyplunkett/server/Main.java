package com.scottyplunkett.server;

import java.io.IOException;

class Main {

    private final Server server;

    private Main(int portNumber) throws IOException {
        this.server = new Server(portNumber);
    }

    private void start() throws IOException {
        System.out.println("Port: " + server.socket.getLocalPort());
    }

    public static void main(String[] args) throws IOException {
        int portNumber = Integer.parseInt(args[0]);
        new Main(portNumber);
    }
}