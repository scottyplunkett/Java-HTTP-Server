package com.scottyplunkett.server.Cycle.Response.Behavior.handlers;

import com.scottyplunkett.server.Cycle.Request.HTTPRequest;

import java.io.IOException;

public class RestrictedMethodHandler extends Handler {
    HTTPRequest httpRequest;
    String date;
    String produced;

    public RestrictedMethodHandler() {}

    RestrictedMethodHandler(HTTPRequest request, String _date) {
        httpRequest = request;
        date = _date;
    }

    private String produce405Response() {
        String head = new HTTPResponseHeaders("405 Method Not Allowed", "text/html", date).get();
        head = head + "Allow: GET\r\n";
        String payLoad = "405: Method Not Allowed... Stick to what you're good at.";
        String responseString = head + "\r\n" + payLoad;
        return responseString;
    }

    @Override
    public byte[] get() {
        return produced.getBytes();
    }

    public void setHttpRequest(HTTPRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void produceContent() throws IOException {
        produced = produce405Response();
    }
}
