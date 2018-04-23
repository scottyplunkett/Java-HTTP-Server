package com.scottyplunkett.server.cycle.response.behavior.handlers;
import com.scottyplunkett.server.cycle.request.HTTPRequest;
import com.scottyplunkett.server.cycle.utils.Date;

import java.io.IOException;

public abstract class Handler {
    HTTPRequest request;
    Date date;

    public abstract byte[] get() throws IOException;

    public abstract void setHttpRequest(HTTPRequest httpRequest);
    public abstract void setDate(String date);

    public abstract void produceContent() throws IOException;
}
