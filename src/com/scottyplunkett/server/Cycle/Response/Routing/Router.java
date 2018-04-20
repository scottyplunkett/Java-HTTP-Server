package com.scottyplunkett.server.Cycle.Response.Routing;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Router {
    public static Path route(String requestLine) {
        if (hasParameters(requestLine))
            return containsCookie(requestLine) ? Paths.get("pages/cookie.html") : Paths.get("pages/dynamic.html");
        else return walkRoutes(requestLine);
    }


    private static Path walkRoutes(String requestLine) {
        switch (requestLine) {
            case "GET / HTTP/1.1" : return Paths.get("public");
            case "GET /nicole HTTP/1.1" : return Paths.get("pages/nicole.html");
            case "GET /paul HTTP/1.1" : return Paths.get("pages/paul.html");
            case "GET /josh HTTP/1.1" : return Paths.get("pages/josh.html");
            case "GET /file1 HTTP/1.1" : return Paths.get("pages/file1");
            case "GET /coffee HTTP/1.1" : return Paths.get("pages/418.html");
            case "GET /image.jpeg HTTP/1.1" : return Paths.get("public/image.jpeg");
            case "GET /image.gif HTTP/1.1" : return Paths.get("public/image.gif");
            case "GET /image.png HTTP/1.1" : return Paths.get("public/image.png");
            case "GET /text-file.txt HTTP/1.1" : return Paths.get("public/text-file.txt");
            case "GET /patch-content.txt HTTP/1.1" : return Paths.get("public/patch-content.txt");
            case "PATCH /patch-content.txt HTTP/1.1" : return Paths.get("public/patch-content.txt");
            case "OPTIONS /method_options HTTP/1.1" : return Paths.get("pages/method_options.html");
            case "OPTIONS /method_options2 HTTP/1.1" : return Paths.get("pages/method_options.html");
            default : return Paths.get("pages/404.html");
        }
    }

    private static boolean containsCookie(String requestLine) {
        return requestLine.contains("cookie");
    }

    private static boolean hasParameters(String requestLine) {
        return requestLine.split("\\?").length > 1;
    }
}
