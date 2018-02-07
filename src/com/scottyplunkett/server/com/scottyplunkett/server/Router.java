package com.scottyplunkett.server;

import java.net.MalformedURLException;

public class Router {
        Router(){}

        public String route(String requestLine) throws MalformedURLException {
            if (requestLine == "GET / HTTP/1.1") {
                return "./helloworld.html";
            }
            else if (requestLine == "GET /nicole HTTP/1.1") {
                return "com/scottyplunkett/server/nicole.html";
            }
            else if (requestLine == "GET /paul HTTP/1.1"){
                return "./paul.html";
            }
            else if (requestLine == "GET /josh HTTP/1.1") {
                return "./josh.html";
            } else {
                return "com/scottyplunkett/server/404.html";
            }
        }
    }

