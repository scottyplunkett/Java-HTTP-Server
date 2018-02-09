package com.scottyplunkett.server;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Router {
        Router(){}

        public Path route(ArrayList<String> request) {
            if ("GET / HTTP/1.1".equals(request.get(0))) {
                return Paths.get("/Users/scottplunkett/Desktop/server/src/com/scottyplunkett/server/com/scottyplunkett/server/helloworld.html");
            }
            else if ("GET /nicole HTTP/1.1".equals(request.get(0))) {
                return Paths.get("/Users/scottplunkett/Desktop/server/src/com/scottyplunkett/server/com/scottyplunkett/server/nicole.html");
            }
            else if ("GET /paul HTTP/1.1".equals(request.get(0))){
                return Paths.get("/Users/scottplunkett/Desktop/server/src/com/scottyplunkett/server/com/scottyplunkett/server/paul.html");
            }
            else if ("GET /josh HTTP/1.1".equals(request.get(0))) {
                return Paths.get("/Users/scottplunkett/Desktop/server/src/com/scottyplunkett/server/com/scottyplunkett/server/josh.html");
            } else {
                return Paths.get("/Users/scottplunkett/Desktop/server/src/com/scottyplunkett/server/com/scottyplunkett/server/404.html");
            }
        }
    }

