package com.scottyplunkett.server;

import java.io.IOException;

abstract class Producer {
    HTTPRequest request;

    abstract byte[] get() throws IOException;

    abstract void setHttpRequest(HTTPRequest httpRequest);
    abstract void setDate(String date);

    abstract void produceContent() throws IOException;
}
