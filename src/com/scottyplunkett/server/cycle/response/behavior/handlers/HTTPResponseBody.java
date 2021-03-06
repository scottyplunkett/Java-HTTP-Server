package com.scottyplunkett.server.cycle.response.behavior.handlers;

import com.scottyplunkett.server.cycle.response.routing.Router;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HTTPResponseBody {
    private String responseBodyContent;

    public HTTPResponseBody(String requested) throws IOException {
        Path path = Router.route(requested);
        responseBodyContent = Files.isDirectory(path) ?
                new DirectoryContent(path).get() :
                new PageContent(path, requested).get();
    }

    public String get() {
        return responseBodyContent;
    }
}
