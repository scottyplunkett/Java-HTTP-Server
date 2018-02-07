package com.scottyplunkett.server;

import com.scottyplunkett.server.reading.Request;

import java.io.BufferedReader;
import java.util.Map;

class HTTPRequest implements Request {
    private String request;

    HTTPRequest(String request) {
        this.request = request;
    }

    public HTTPRequest(BufferedReader in) {

    }

    public void handle(String request) {
    }

    public void headers() {
    }

    @Override
    public Map<String, String> mapRequestHeadersToContent() {
        return null;
    }

    @Override
    public String getRequestLine() {
        return null;
    }
}

