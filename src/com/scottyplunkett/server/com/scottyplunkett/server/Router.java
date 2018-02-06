package com.scottyplunkett.server;

public class Router {
        Router(){}

        public String route(String requestLine) {
            String path = null;
            if (requestLine == "GET / HTTP/1.1") {
                path = "/helloworld.html";
            }
            else if (requestLine == "GET /nicole HTTP/1.1") {
                path = "/nicole.html";
            }
            else if (requestLine == "GET /paul HTTP/1.1"){
                path = "/paul.html";
            }
            else if (requestLine == "GET /josh HTTP/1.1") {
                path = "/josh.html";
            } else {
                path = "/404.html";
            }
            return path;
        }
    }

