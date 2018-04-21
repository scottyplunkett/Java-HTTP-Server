package com.scottyplunkett.server.Cycle.Response.Behavior.Handlers;
import com.scottyplunkett.server.Cycle.Request.HTTPRequest;
import com.scottyplunkett.server.Cycle.Utils.Date;

import java.io.IOException;

public abstract class Handler {
    HTTPRequest request;
    Date date;

    public abstract byte[] get() throws IOException;

    public abstract void setHttpRequest(HTTPRequest httpRequest);
    public abstract void setDate(String date);

    public abstract void produceContent() throws IOException;
}
