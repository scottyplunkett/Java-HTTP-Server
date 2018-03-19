package com.scottyplunkett.server;

class HTTPResponseHeaders {
    private static final String CRLF = "\r\n";
    private String responseHeadersContent;

    HTTPResponseHeaders(String status, String contentType, String date) {
        String statusLine = "HTTP/1.1 " + status + CRLF;
        String locationLine = "Location: /\r\n";
        String dateLine = "Date: " + date + CRLF;
        String contentTypeLine = "Content-Type: " + contentType + CRLF;
        if ("302 Found".equals(status)) statusLine = statusLine + locationLine;
        responseHeadersContent = statusLine + dateLine + contentTypeLine;
    }

    String get() {
        return responseHeadersContent;
    }
}
