package com.scottyplunkett.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Boolean socketOpen = false;

    public void main(String[] args) throws IOException {
        new Server(Integer.parseInt(args[0]));
    }

    public Server(int portNumber) throws IOException {

        this.setServerSocket(new ServerSocket(portNumber));
        while (!serverSocket.isClosed()) {
            Socket socket = getServerSocket().accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String s;
            while ((s = in.readLine()) != null) {
                System.out.println(s);
                if (s.isEmpty()) {
                    break;
                }
            }

            out.write("HTTP/1.0 200 OK\r\n");
            out.write("Date: Thu, 25 Jan 2018 00:59:59 GMT\r\n");
            out.write("Server: Apache/0.8.4\r\n");
            out.write("Content-Type: text/html\r\n");
            out.write("Expires: Sat, 01 Jan 2020 00:59:59 GMT\r\n");
            out.write("Last-modified: Fri, 09 Aug 2017 14:21:40 GMT\r\n");
            out.write("\r\n");
            out.write("<h1>Hello, World!</h1>");
            out.write("<p>hehe.</p>");

            System.err.println("Connection to client terminated!");
            out.close();
            in.close();
        }
    }


    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
}
