package com.scottyplunkett.server.Cycle.Response.Behavior.handlers;

import com.scottyplunkett.server.Cycle.Request.HTTPRequest;
import com.scottyplunkett.server.Cycle.Response.Routing.Router;
import com.scottyplunkett.server.Cycle.Utils.Parser;

import java.io.IOException;
import java.nio.file.Files;

public class DefaultHandler extends Handler {
    public HTTPRequest httpRequest;
    public String date;
    public String produced;

    public DefaultHandler() {}

    public DefaultHandler(HTTPRequest request, String _date) {
        httpRequest = request;
        date = _date;
    }

    private String produceBasicResponse() throws IOException {
        String requestLine = httpRequest.getRequestLine();
        String requestedRoute = Parser.findRequestedRoute(requestLine);
        int encoded = HTTPResponseCode.encode(requestedRoute);
        String responseCode = HTTPResponseCode.retrieve(encoded);
        HTTPResponseHeaders headers = setHeaders(requestLine, date, responseCode);
        HTTPResponseBody body = new HTTPResponseBody(requestLine);
        return headers.get() + "\r\n" + body.get();
    }

    private HTTPResponseHeaders setHeaders(String requested, String date, String responseCode) throws IOException {
        String contentType = Files.probeContentType(Router.route(requested));
        if(requested.equals("OPTIONS /method_options HTTP/1.1"))
            return new HTTPResponseHeaders(responseCode, contentType, date, "GET,HEAD,POST,OPTIONS,PUT");
        if(requested.equals("OPTIONS /method_options2 HTTP/1.1"))
            return new HTTPResponseHeaders(responseCode, contentType, date, "GET,OPTIONS");
        else
            return new HTTPResponseHeaders(responseCode, contentType, date);
    }

    @Override
    public byte[] get() {
        return produced.getBytes();
    }

    @Override
    public void setHttpRequest(HTTPRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void produceContent() throws IOException {
        produced = produceBasicResponse();
    }
}
