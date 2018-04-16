package com.scottyplunkett.server.Cycle.Routing;

import com.scottyplunkett.server.Cycle.Behavior.handlers.Handler;

import java.nio.file.Path;

interface Route {
    Path getPath();
    Handler getHandler();
}
