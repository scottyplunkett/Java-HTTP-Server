package com.scottyplunkett.server.Cycle.Routing;

import com.scottyplunkett.server.Cycle.Behavior.handlers.Handler;

import java.nio.file.Path;

class HTTPRoute implements Route {
    Path path;
    Handler handler;

    HTTPRoute(){}

    public void setPath(Path path) {
        this.path = path;
    }

    public void setHandler(Path path) {
        this.path = path;
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }
}
