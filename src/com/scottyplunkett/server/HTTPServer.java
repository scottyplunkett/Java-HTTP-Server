package com.scottyplunkett.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class HTTPServer {
    public static void main() throws IOException {
        main(new String[]{"5000"});
    }

    public static void main(String[] args) throws IOException {
            int port = Integer.parseInt(args[0]);
            run(port);
    }

    private static void run(int port) throws IOException {
        DocumentTags tags = new DocumentTags();
        ServerSocket internalConnection = connect(port);
        while (!internalConnection.isClosed()) {
            Socket connection = internalConnection.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            serve(connection, in, out, tags);
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
                              BufferedWriter out,
                              DocumentTags tags) throws IOException {
        HTTPRequest request = new HTTPRequest(in);
        String response = request.getEtag() == null ?
                new HTTPResponse(request.getRequestLine()).get() :
                new TaggedDocumentHTTPResponse(request, tags).get();
        out.write(response);
        out.flush();
        System.err.println("Client served…");
        out.close();
        in.close();
        socket.close();
        System.err.println("Connection Terminated!");
    }
}