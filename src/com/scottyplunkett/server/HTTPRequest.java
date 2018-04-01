package com.scottyplunkett.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

class HTTPRequest {
    private ArrayList<String> requestContent;
    private String requestLine;
    private String eTag;
    public  String cookie;
    private String authorization;
    private String body;
    private BufferedReader reader;

    HTTPRequest(InputStream in) throws IOException {
        requestContent = new ArrayList<>();
        reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder fullRequest = new StringBuilder();
        int nextChar;
        while ((nextChar = reader.read()) != -1) {
            fullRequest.append((char)nextChar);
            if (!reader.ready()) {
                break;
            }
        }
        String requestString = fullRequest.toString();
        System.out.println(requestString);
        Collections.addAll(requestContent, requestString.split("\r\n"));
        requestLine = requestContent.get(0);
        setEtag();
        setCookie();
        setAuthorization();
        setBody();
    }

    private void setEtag() {
        requestContent.forEach(line -> {
           if (line.startsWith("If-Match") || line.startsWith("If-None-Match")) eTag = line.split("\\s")[1];
        });
    }

    private void setCookie() {
        if(requestLine.contains("cookie?")) cookie = requestLine.split("\\s")[1].split("\\=")[1];
        else requestContent.forEach(line -> {
            if (line.startsWith("Cookie")) cookie = line.split("\\s")[1];
        });
    }

    private void setBody() {
        int index = requestContent.size() - 1;
        body = requestContent.get(index);
    }

    String getRequestLine() {
        return requestLine;
    }

    String getEtag() {
        return eTag;
    }

    String getBody() {
        return body;
    }

    String getCookie() {
        return cookie;
    }

    String getAuthorization() {
        return authorization;
    }

    private void setAuthorization() {
        requestContent.forEach(line -> {
            if (line.startsWith("Authorization")) authorization = line.split(":\\s")[1];
        });
    }
}

