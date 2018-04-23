package com.scottyplunkett.server.cycle.response.behavior.handlers;

import com.scottyplunkett.server.cycle.request.HTTPRequest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultHandlerTest {
    String requestLine;
    HTTPRequest httpRequest;
    DefaultHandler defaultHandler;

    void setUp(String requestLine) throws IOException {
        InputStream in = new ByteArrayInputStream(requestLine.getBytes());
        httpRequest = new HTTPRequest(in);
        defaultHandler = new DefaultHandler(httpRequest, "mockdate");
        defaultHandler.produceContent();
    }

    @Test
    void get() throws IOException {
        setUp("GET /nicole HTTP/1.1");
        Path filePath = Paths.get("pages/nicole.html");
        String fileContents = Files.lines( filePath ).collect( Collectors.joining() );
        String expectedHeaders = "HTTP/1.1 200 OK\r\nDate: mockdate\r\nContent-Type: text/html\r\n";
        String expectedResponse = expectedHeaders + "\r\n" + fileContents;
        assertArrayEquals(expectedResponse.getBytes(), defaultHandler.get());
    }

    @Test
    void setHttpRequest() throws IOException {
        defaultHandler = new DefaultHandler();
        requestLine = "GET /paul HTTP/1.1";
        InputStream in = new ByteArrayInputStream(requestLine.getBytes());
        httpRequest = new HTTPRequest(in);
        defaultHandler.setHttpRequest(httpRequest);
        assertEquals("GET /paul HTTP/1.1", defaultHandler.httpRequest.getRequestLine());
    }

    @Test
    void setDate() {
        String date = "Date Set";
        defaultHandler = new DefaultHandler();
        defaultHandler.setDate(date);
        assertEquals("Date Set", defaultHandler.date);
    }

    @Test
    void produceContent() throws IOException {
        setUp("GET /pages?variable_1=Scott&variable_2=Plunkett HTTP/1.1");
        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                "Date: mockdate\r\n" +
                "Content-Type: text/html\r\n";
        String contentToInsert = "<p>variable_1 = Scott</p><br><p>variable_2 = Plunkett</p><br>";
        String content = Files.lines( Paths.get("pages/dynamic.html") ).collect( Collectors.joining() );
        content = content.replace("$content", contentToInsert);
        String expectedProduced = expectedHeaders + "\r\n" + content;
        defaultHandler.produceContent();
        assertEquals(expectedProduced, defaultHandler.produced);
    }
}