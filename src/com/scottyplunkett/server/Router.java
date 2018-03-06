package com.scottyplunkett.server;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Router {
        public static Path route(String requestLine) {
            return hasParameters(requestLine) ? Paths.get("pages/dynamic.html") : walkRoutes(requestLine);
        }

    private static Path walkRoutes(String requestLine) {
        switch (requestLine) {
            case "GET / HTTP/1.1" : return Paths.get("public");
            case "GET /nicole HTTP/1.1" : return Paths.get("pages/nicole.html");
            case "GET /paul HTTP/1.1" : return Paths.get("pages/paul.html");
            case "GET /josh HTTP/1.1" : return Paths.get("pages/josh.html");
            case "GET /file1 HTTP/1.1" : return Paths.get("pages/file1");
            default : return Paths.get("pages/404.html");
        }
    }

    private static boolean hasParameters(String requestLine) {
        return requestLine.split("\\?").length > 1 ? true : false;
    }
}
