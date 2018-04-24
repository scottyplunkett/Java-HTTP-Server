package com.scottyplunkett.server.cycle.utils;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

    @Test
    void findRequestMethod(){
        String getRequest = "GET /pages?first=Michael&last=Scarn HTTP/1.1";
        assertEquals("GET", Parser.findRequestMethod(getRequest));
        String postRequest = "POST /pages?first=Michael&last=Scarn HTTP/1.1";
        assertEquals("POST", Parser.findRequestMethod(postRequest));
        String deleteRequest = "DELETE /pages?first=Michael&last=Scarn HTTP/1.1";
        assertEquals("DELETE", Parser.findRequestMethod(deleteRequest));
    }

    @Test
    void findRequestedRoute() {
        assertEquals("/nicole", Parser.findRequestedRoute("GET /nicole HTTP/1.1"));
        assertEquals("/paul", Parser.findRequestedRoute("GET /paul HTTP/1.1"));
        assertEquals("/josh", Parser.findRequestedRoute("GET /josh HTTP/1.1"));
        assertEquals("/pages", Parser.findRequestedRoute("GET /pages?first=Michael&last=Scarn HTTP/1.1"));
        assertEquals("/nicole", Parser.findRequestedRoute("GET /nicole?first=Michael&last=Scarn HTTP/1.1"));
    }


    @Test
    void decodeURLSafeString() throws UnsupportedEncodingException {
        String decoded = "Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
        String encoded = "Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F";
        assertEquals(decoded, Parser.decodeURLSafeString(encoded));
    }
}