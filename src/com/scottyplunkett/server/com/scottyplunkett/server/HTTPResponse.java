package com.scottyplunkett.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HTTPResponse {

//    public void send(BufferedWriter out, String body) throws IOException {
//        out.write("HTTP/1.0 200 OK\r\n");
//        out.write("Date: Thu, 25 Jan 2018 00:59:59 GMT\r\n");
//        out.write("HTTPServer: Apache/0.8.4\r\n");
//        out.write("Content-Type: text/html\r\n");
//        out.write("Expires: Sat, 01 Jan 2020 00:59:59 GMT\r\n");
//        out.write("Last-modified: Fri, 09 Aug 2017 14:21:40 GMT\r\n");
//        out.write("\r\n");
//        out.write(body);
//        out.close();
//    };


    public void send(BufferedWriter out, String path) throws IOException {
        out.write("HTTP/1.0 200 OK\r\n");
        out.write("Date: Thu, 25 Jan 2018 00:59:59 GMT\r\n");
        out.write("HTTPServer: Apache/0.8.4\r\n");
        out.write("Content-Type: text/html\r\n");
        out.write("Expires: Sat, 01 Jan 2020 00:59:59 GMT\r\n");
        out.write("Last-modified: Fri, 09 Aug 2017 14:21:40 GMT\r\n");
        out.write("\r\n");
        Files.lines(Paths.get(path), StandardCharsets.UTF_8).forEach(str -> {
            try {
                out.write(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        out.close();
    };

    public static int getCode(String httpResponse) {
        return 200;
    }
}
