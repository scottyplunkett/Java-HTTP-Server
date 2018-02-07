package com.scottyplunkett.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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

            HTTPResponse httpResponse;
            httpResponse = new HTTPResponse();
            httpResponse.send(out, new Router().route(request.get(0)));
            System.err.println("Connection to client terminated!");
            out.close();
            in.close();
            socket.close();
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