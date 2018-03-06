package com.scottyplunkett.server;

import java.io.IOException;

class HTTPResponse {
    static String body;

    static void setBody(String htmlString) {
        body = htmlString;
    }

    static String build(String requested) throws IOException {
        return build(requested, Date.getDate());
    }

    static String build(String requested, String date) throws IOException {
        String route = Parser.findRequestedRoute(requested);
        int encoded = HTTPResponseCode.encode(route);
        String responseHeaders = HTTPHeaders.build(HTTPResponseCode.retrieve(encoded), "text/html", date);
        String responseBody = HTTPResponseBody.getResponseBodyContent(requested);
        return responseHeaders + "\r\n" + responseBody;
    }


}