package com.scottyplunkett.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    String request;
    ServerSocket serverSocket;
    Socket socket;


    public Server(int portNumber) throws IOException {
        List<String> HTTPRequest = new ArrayList<String>();
        this.serverSocket = new ServerSocket(portNumber);
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while ((request = in.readLine()) != null) {
                System.out.print(request);
                HTTPRequest.add(request);
                if (request.isEmpty()) {
                    break;
                }
            }
            System.out.print(HTTPRequest);
            System.out.println(route(HTTPRequest.get(0).toString()));
            out.write("HTTP/1.0 200 OK\r\n");
            out.write("Date: Thu, 25 Jan 2018 00:59:59 GMT\r\n");
            out.write("Server: Apache/0.8.4\r\n");
            out.write("Content-Type: text/html\r\n");
            out.write("Expires: Sat, 01 Jan 2020 00:59:59 GMT\r\n");
            out.write("Last-modified: Fri, 09 Aug 2017 14:21:40 GMT\r\n");
            out.write("\r\n");
            out.write("<h1>Hello, World!</h1>");
            out.write(route(request));

            System.err.println("Connection to client terminated!");
            out.close();
            in.close();
        }
    }

    boolean isListening() {
        return this.serverSocket.isBound();
    }

    public static String route(String request) {
        if (request == "GET / HTTP/1.1") {
            return "/helloworld.html";
        }
        else if (request == "GET /nicole HTTP/1.1") {
            return "/nicole.html";
        }
        else if (request == "GET /paul HTTP/1.1"){
            return "/paul.html";
        }
        else {
            return "/josh.html";
        }
    }
}
