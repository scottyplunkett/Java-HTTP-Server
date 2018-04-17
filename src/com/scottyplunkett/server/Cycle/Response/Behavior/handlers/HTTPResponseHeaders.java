package com.scottyplunkett.server.Cycle.Response.Behavior.handlers;

public class HTTPResponseHeaders {
    private static final String CRLF = "\r\n";
    private String responseHeadersContent;
    private String statusLine;
    private String locationLine;
    private String dateLine;
    private String contentTypeLine;

    public HTTPResponseHeaders(String status, String contentType, String date) {
        statusLine = "HTTP/1.1 " + status + CRLF;
        locationLine = "Location: /\r\n";
        dateLine = "Date: " + date + CRLF;
        contentTypeLine = "Content-Type: " + contentType + CRLF;
        if ("302 Found".equals(status)) statusLine = statusLine + locationLine;
        responseHeadersContent = statusLine + dateLine + contentTypeLine;
    }

    public HTTPResponseHeaders(String status, String contentType, String date, String options) {
        String optionsLine;
        statusLine = "HTTP/1.1 " + status + CRLF;
        optionsLine = "Allow: " + options + CRLF;
        dateLine = "Date: " + date + CRLF;
        contentTypeLine = "Content-Type: " + contentType + CRLF;
        responseHeadersContent = statusLine + optionsLine + dateLine + contentTypeLine;
    }

    public String get() {
        return responseHeadersContent;
    }
}
