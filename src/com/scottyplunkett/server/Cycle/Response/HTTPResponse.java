package com.scottyplunkett.server.Cycle.Response;

import com.scottyplunkett.server.Cycle.Utils.Date;
import com.scottyplunkett.server.Cycle.Request.HTTPRequest;
import com.scottyplunkett.server.Cycle.Response.Behavior.handlers.Handler;

import java.io.IOException;

public class HTTPResponse {
    private Handler handler;
    private String date;
    private HTTPRequest httpRequest;

    public HTTPResponse(HTTPRequest request, Handler _handler) throws IOException {
        this(request, Date.getDate(), _handler);
    }

    HTTPResponse(HTTPRequest request, String _date, Handler _handler) throws IOException {
        httpRequest = request;
        date = _date;
        handler = _handler;
        setUpHandler();
        produceContent();
    }

    public byte[] get() throws IOException { return handler.get(); }

    private void produceContent() throws IOException { handler.produceContent(); }

    private void setUpHandler() {
        handler.setHttpRequest(httpRequest);
        handler.setDate(date);
    }
}