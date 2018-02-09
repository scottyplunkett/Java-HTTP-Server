package com.scottyplunkett.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class HTTPServer {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            run();
        } else {
            run(args);
        }

    }

    public static void run() throws IOException {
        run(new String[]{"4444"});
    }

    public static void run(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String s;
            ArrayList<String> request = new ArrayList<>();
            while ((s = in.readLine()) != null) {
                request.add(s);
                System.out.println(s);
                if (s.isEmpty()) {
                    break;
                }
            }
            Path route = new Router().route(request);
            String stringFromFile = java.nio.file.Files.lines(route).collect(
                    Collectors.joining());
            System.out.print(stringFromFile);
            String response = "HTTP/1.0 200 OK\r\n"+
                    "Date: Thu, 25 Jan 2018 00:59:59 GMT\r\n"+
                    "HTTPServer: Apache/0.8.4\r\n"+
                    "Content-Type: text/html\r\n"+
                    "Expires: Sat, 01 Jan 2020 00:59:59 GMT\r\n"+
                    "Last-modified: Fri, 09 Aug 2017 14:21:40 GMT\r\n"+
                    "\r\n"+
                    stringFromFile;
            out.write(response);
            out.flush();
            out.close();
            in.close();
            socket.close();
            System.err.println("Connection to client terminated!");
        }
    }

    public HTTPServer() throws IOException {}


    public static ServerSocket setUp(int portNumber) throws IOException {
        ServerSocket serverSocket = new ServerSocket(portNumber);
        return serverSocket;
    }

    public static String serve(String request) {
            return "HTTP/1.0 200 OK\n\r";
        }
    }