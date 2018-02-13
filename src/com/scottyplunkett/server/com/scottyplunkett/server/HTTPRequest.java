package com.scottyplunkett.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

class HTTPRequest {
    static ArrayList<String> read(BufferedReader in) throws IOException {
        String s;
        ArrayList<String> request = new ArrayList<>();
        while ((s = in.readLine()) != null) {
            request.add(s);
            System.out.println(s);
            if (s.isEmpty()) {
                break;
            }
        }
        return request;
    }
}

