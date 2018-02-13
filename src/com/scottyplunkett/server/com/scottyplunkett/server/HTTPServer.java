package com.scottyplunkett.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;

public class HTTPServer {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            run(4444);
        } else {
            int port = Integer.parseInt(args[0]);
            run(port);
        }

    }

    private static void run(int port) throws IOException {
        ServerSocket internalConnection = connect(port);
        while (!internalConnection.isClosed()) {
            Socket connection = internalConnection.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            serve(connection, in, out);
        }
    }

    static ServerSocket connect(int portNumber) throws IOException {
        return new ServerSocket(portNumber);
    }

    void close(ServerSocket serverSocket) throws IOException {
        serverSocket.close();
    }

    private static void serve(Socket socket,
                              BufferedReader in,
                              BufferedWriter out) throws IOException {
            String requestLine = HTTPRequest.read(in).get(0);
            Path resource = Router.route(requestLine);
            String response = HTTPResponse.build(resource);
            out.write(response);
            out.flush();
            out.close();
            in.close();
            socket.close();
            System.err.println("Connection to client terminated!");
        }
    }