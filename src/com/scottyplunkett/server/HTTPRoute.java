package com.scottyplunkett.server;

import java.nio.file.Path;

class HTTPRoute implements Route {
    Path path;
    Producer producer;

    HTTPRoute(){}

    public void setPath(Path path) {
        this.path = path;
    }

    public void setProducer(Path path) {
        this.path = path;
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public Producer getProducer() {
        return producer;
    }
}
