package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPResponseHeadersTest {

    @Test
    void build200Headers() {
        String date = Date.getDate();
        String expectedHeaders =
                "HTTP/1.1 200 OK\r\n" + "Date: " + date + "\r\n" + "Content-Type: text/html\r\n";
        String actualHeaders = new HTTPResponseHeaders("200 OK", "text/html", date).get();
        assertEquals(expectedHeaders, actualHeaders);
    }

    @Test
    void build302Headers() {
        String date = Date.getDate();
        String expectedHeaders = "HTTP/1.1 302 Found\r\n" +
                                 "Location: /\r\n" +
                                 "Date: " + date + "\r\n" +
                                 "Content-Type: text/html\r\n";
        String actualHeaders = new HTTPResponseHeaders("302 Found", "text/html", date).get();
        assertEquals(expectedHeaders, actualHeaders);
    }
}