package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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
    void mapQuery() throws UnsupportedEncodingException {
        Map <String, String> expectedQueryHashMap1 = new HashMap<>();
        expectedQueryHashMap1.put("first", "Michael");
        expectedQueryHashMap1.put("last", "Scarn");
        String request = "GET /pages?first=Michael&last=Scarn HTTP/1.1";
        Map <String, String> actualQueryHashMap1 = Parser.mapQuery(request);
        assertEquals(expectedQueryHashMap1, actualQueryHashMap1);

        Map <String, String> expectedQueryHashMap2 = new HashMap<>();
        expectedQueryHashMap2.put("non", "sense");
        expectedQueryHashMap2.put("test", "data");
        String testRequest = "GET /pages?non=sense&test=data HTTP/1.1";
        Map <String, String> actualQueryHashMap2 = Parser.mapQuery(testRequest);
        assertEquals(expectedQueryHashMap2, actualQueryHashMap2);
    }

    @Test
    void parseForMethod() {
        String getRequest = "GET /pages?first=Michael&last=Scarn HTTP/1.1";
        assertEquals("GET", Parser.parseFor("method", getRequest));
        String postRequest = "POST /pages?first=Michael&last=Scarn HTTP/1.1";
        assertEquals("POST", Parser.parseFor("method", postRequest));
        String deleteRequest = "DELETE /pages?first=Michael&last=Scarn HTTP/1.1";
        assertEquals("DELETE", Parser.parseFor("method", deleteRequest));
    }

    @Test
    void decodeURLSafeString() throws UnsupportedEncodingException {
        String decoded = "Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
        String encoded = "Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F";
        assertEquals(decoded, Parser.decodeURLSafeString(encoded));
    }
}