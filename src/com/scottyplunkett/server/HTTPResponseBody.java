package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class HTTPResponseBody {
    private String responseBodyContent;

    HTTPResponseBody(String requested) throws IOException {
        Path path = Router.route(requested);
        responseBodyContent = Files.isDirectory(path) ?
                new DirectoryContent(path).get() :
                new PageContent(path, requested).get();
    }

    String get() {
        return responseBodyContent;
    }
}
