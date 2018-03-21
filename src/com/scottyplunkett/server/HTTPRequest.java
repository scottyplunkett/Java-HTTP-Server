package com.scottyplunkett.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

class HTTPRequest {
    private ArrayList<String> requestContent;
    private String requestLine;
    private String eTag;

    HTTPRequest(BufferedReader in) throws IOException {
        requestContent = new ArrayList<>();
        StringBuilder fullRequest = new StringBuilder();
        int nextChar;
        while ((nextChar = in.read()) != -1) {
            fullRequest.append((char)nextChar);
            if (!in.ready()) {
                break;
            }
        }
        System.out.println(fullRequest.toString());
        Collections.addAll(requestContent, fullRequest.toString().split("\r\n"));
        requestLine = requestContent.get(0);
        setEtag();
    }

    private void setEtag() {
        String secondLineHeader = requestContent.get(1).split("\\s")[0];
        if ("If-Match:".equals(secondLineHeader) || "If-None-Match:".equals(secondLineHeader)) {
            eTag = requestContent.get(1).split("\\s")[1];
        }
    }

    String getRequestLine() {
        return requestLine;
    }

    String getEtag() {
        return eTag;
    }
}

