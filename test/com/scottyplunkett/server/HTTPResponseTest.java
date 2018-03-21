package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPResponseTest {

    @Test
    void get() throws IOException {
        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                                 "Date: bla\r\n" +
                                 "Content-Type: text/html\r\n";
        String nicoleRequest = "GET /nicole HTTP/1.1";
        Path filePath = Router.route(nicoleRequest);
        String content = Files.lines( filePath ).collect( Collectors.joining() );
        assertEquals(expectedHeaders + "\r\n" + content, new HTTPResponse(nicoleRequest, "bla").get());
    }

    @Test
    void getResponseToRequestWithParams() throws IOException {
        String expectedGeneratedContent = "<p>variable_1 = Scott</p><br><p>variable_2 = Plunkett</p><br>";
        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                                 "Date: bla\r\n" +
                                 "Content-Type: text/html\r\n";
        String requestedWithParams = "GET /pages?variable_1=Scott&variable_2=Plunkett HTTP/1.1";
        Path templatePath = Router.route(requestedWithParams);
        String content = Files.lines( templatePath ).collect( Collectors.joining() );
        content = content.replace("$content", expectedGeneratedContent);
        assertEquals(expectedHeaders + "\r\n" + content, new HTTPResponse(requestedWithParams, "bla").get());
    }

    @Test
    void getResponseToOptionsRequestForMethodOptions() throws IOException {
        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                                 "Allow: GET,HEAD,POST,OPTIONS,PUT\r\n" +
                                 "Date: bla\r\n" +
                                 "Content-Type: text/html\r\n";
        String requestMethodOptions = "OPTIONS /method_options HTTP/1.1";
        assertEquals(expectedHeaders + "\r\n" + "", new HTTPResponse(requestMethodOptions, "bla").get());
    }

    @Test
    void getResponseToOptionsRequestForMethodOptions2() throws IOException {
        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                                 "Allow: GET,OPTIONS\r\n" +
                                 "Date: bla\r\n" +
                                 "Content-Type: text/html\r\n";
        String requestMethodOptions2 = "OPTIONS /method_options2 HTTP/1.1";
        assertEquals(expectedHeaders + "\r\n" + "", new HTTPResponse(requestMethodOptions2, "bla").get());
    }

    @Test
    void getResponsetoGetRequestforPatchContent() throws IOException {
        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                                 "Date: bla\r\n" +
                                 "Content-Type: text/html\r\n";
        String requestGetPatchContent = "GET /patch-content.txt HTTP/1.1";
        String expectedResponse = expectedHeaders + "\r\n" + "default content";
        assertEquals(expectedResponse, new HTTPResponse(requestGetPatchContent, "bla").get());
    }
}