package com.scottyplunkett.server;

import java.nio.file.Path;

interface Route {
    Path getPath();
    Producer getProducer();
}
