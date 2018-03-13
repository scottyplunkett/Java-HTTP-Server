package com.scottyplunkett.server;

public class HTTPHeaders {
    public static final String CRLF = "\r\n";

    static String build(String status, String contentType, String date) {
        String statusLine = "HTTP/1.1 " + status + CRLF;
        String locationLine = "Location: /\r\n";
        String dateLine = "Date: " + date + CRLF;
        String contentTypeLine = "Content-Type: " + contentType + CRLF;
        if ("302 Found".equals(status)) statusLine = statusLine + locationLine;
        return statusLine + dateLine + contentTypeLine;
    }

}
