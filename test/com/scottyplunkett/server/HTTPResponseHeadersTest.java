package com.scottyplunkett.server;

import com.scottyplunkett.server.Cycle.Date;
import com.scottyplunkett.server.Cycle.HTTPResponseHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPResponseHeadersTest {
    String date;

    @BeforeEach
    void setDate() {
        date = Date.getDate();
    }

    @Test
    void build200Headers() {
        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                                 "Date: " + date + "\r\n" +
                                 "Content-Type: text/html\r\n";
        String actualHeaders = new HTTPResponseHeaders("200 OK", "text/html", date).get();
        assertEquals(expectedHeaders, actualHeaders);
    }

    @Test
    void build302Headers() {
        String expectedHeaders = "HTTP/1.1 302 Found\r\n" +
                                 "Location: /\r\n" +
                                 "Date: " + date + "\r\n" +
                                 "Content-Type: text/html\r\n";
        String actualHeaders = new HTTPResponseHeaders("302 Found", "text/html", date).get();
        assertEquals(expectedHeaders, actualHeaders);
    }

    @Test
    void buildHeadersWithOptions() {
        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                                 "Allow: GET,HEAD,POST,OPTIONS,PUT\r\n" +
                                 "Date: " + date + "\r\n" +
                                 "Content-Type: text/html\r\n";
        String actualHeaders = new HTTPResponseHeaders("200 OK", "text/html", date,
                                                       "GET,HEAD,POST,OPTIONS,PUT").get();
        assertEquals(expectedHeaders, actualHeaders);
    }

    @Test
    void buildHeadersWithOptions2() {
        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                                 "Allow: GET,OPTIONS\r\n" +
                                 "Date: " + date + "\r\n" +
                                 "Content-Type: text/html\r\n";
        String actualHeaders = new HTTPResponseHeaders("200 OK", "text/html", date, "GET,OPTIONS").get();
        assertEquals(expectedHeaders, actualHeaders);
    }
}