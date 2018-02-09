package com.scottyplunkett.server.reading;

import java.util.Map;

public interface Request {
    public Map<String, String> mapRequestHeadersToContent();

    public String getRequestLine();

}
