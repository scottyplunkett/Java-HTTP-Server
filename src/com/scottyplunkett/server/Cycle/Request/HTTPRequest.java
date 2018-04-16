package com.scottyplunkett.server.Cycle.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class HTTPRequest {
    private ArrayList<String> requestContent;
    private String requestLine;
    private String eTag;
    public  String cookie;
    private String authorization;
    private String body;
    private String range;
    private BufferedReader reader;

    public HTTPRequest(InputStream in) throws IOException {
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
        Collections.addAll(requestContent, requestString.split("\r\n"));
        requestLine = requestContent.get(0);
        setEtag();
        setCookie();
        setAuthorization();
        setRange();
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

    public String getRequestLine() {
        return requestLine;
    }

    public String getEtag() {
        return eTag;
    }

    public String getBody() {
        return body;
    }

    public String getCookie() {
        return cookie;
    }

    public String getAuthorization() {
        return authorization;
    }

    public String getRange() {
        return range;
    }

    private void setAuthorization() {
        requestContent.forEach(line -> {
            if (line.startsWith("Authorization")) authorization = line.split(":\\s")[1];
        });
    }

    private void setRange() {
        int value = 1;
        requestContent.forEach(line -> {
            if (line.startsWith("Range")) {
                range = line.split("\\=")[value];
            }
        });
    }
}

