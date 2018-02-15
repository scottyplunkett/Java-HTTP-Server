package com.scottyplunkett.server;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Router {
        public static Path route(String requestLine) {
            if ("GET / HTTP/1.1".equals(requestLine))
                return Paths.get("pages/helloworld.html");
            else if ("GET /nicole HTTP/1.1".equals(requestLine))
                return Paths.get("pages/nicole.html");
            else if ("GET /paul HTTP/1.1".equals(requestLine))
                return Paths.get("pages/paul.html");
            else if ("GET /josh HTTP/1.1".equals(requestLine))
                return Paths.get("pages/josh.html");
            else if (requestLine.split("\\?").length <= 1) {
                    return Paths.get("pages/404.html");
                } else {
                    return Paths.get("pages/dynamic.html");
            }
        }
}

