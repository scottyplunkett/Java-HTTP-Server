package com.scottyplunkett.server;

import java.io.IOException;

abstract class Producer {
    abstract byte[] get() throws IOException;

    public abstract void setHttpRequest(HTTPRequest httpRequest);
    public abstract void setDate(String date);

    public abstract void produceContent() throws IOException;
}
