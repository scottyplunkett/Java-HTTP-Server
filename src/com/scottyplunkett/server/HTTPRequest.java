package com.scottyplunkett.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

class HTTPRequest {
    private ArrayList<String> requestContent;
    private String requestLine;

    HTTPRequest(BufferedReader in) throws IOException {
        requestContent = new ArrayList<>();
        String s;
        while ((s = in.readLine()) != null) {
            requestContent.add(s);
            if (s.isEmpty()) {
                break;
            }
        }
        requestLine = requestContent.get(0);
    }

    String getRequestLine() {
        return requestLine;
    }
}

