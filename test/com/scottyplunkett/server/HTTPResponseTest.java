package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPResponseTest {

    @Test
    void get() throws IOException {
        String nicoleRequest = "GET /nicole HTTP/1.1\r\nline2\r\nline3\r\n";
        InputStream stream = new ByteArrayInputStream(nicoleRequest.getBytes());
        InputStreamReader feed = new InputStreamReader(stream);
        BufferedReader in = new BufferedReader(feed);
        HTTPRequest request = new HTTPRequest(in);

        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                                 "Date: bla\r\n" +
                                 "Content-Type: text/html\r\n";
        Path filePath = Router.route(request.getRequestLine());
        String content = Files.lines( filePath ).collect( Collectors.joining() );
        assertEquals(expectedHeaders + "\r\n" + content, new HTTPResponse(request, "bla").get());
    }

    @Test
    void getResponseToRequestWithParams() throws IOException {
        String requestedWithParams =
                "GET /pages?variable_1=Scott&variable_2=Plunkett HTTP/1.1\r\nline2\nline3\n";
        InputStream stream = new ByteArrayInputStream(requestedWithParams.getBytes());
        InputStreamReader feed = new InputStreamReader(stream);
        BufferedReader in = new BufferedReader(feed);
        HTTPRequest request = new HTTPRequest(in);

        String expectedGeneratedContent = "<p>variable_1 = Scott</p><br><p>variable_2 = Plunkett</p><br>";
        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                                 "Date: bla\r\n" +
                                 "Content-Type: text/html\r\n";

        Path templatePath = Router.route(requestedWithParams);
        String content = Files.lines( templatePath ).collect( Collectors.joining() );
        content = content.replace("$content", expectedGeneratedContent);
        assertEquals(expectedHeaders + "\r\n" + content, new HTTPResponse(request, "bla").get());
    }

    @Test
    void getResponseToOptionsRequestForMethodOptions() throws IOException {
        String requestMethodOptions = "OPTIONS /method_options HTTP/1.1\r\nline2\nline3\n";
        InputStream stream = new ByteArrayInputStream(requestMethodOptions.getBytes());
        InputStreamReader feed = new InputStreamReader(stream);
        BufferedReader in = new BufferedReader(feed);
        HTTPRequest request = new HTTPRequest(in);

        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                                 "Allow: GET,HEAD,POST,OPTIONS,PUT\r\n" +
                                 "Date: bla\r\n" +
                                 "Content-Type: text/html\r\n";

        assertEquals(expectedHeaders + "\r\n" + "", new HTTPResponse(request, "bla").get());
    }

    @Test
    void getResponseToOptionsRequestForMethodOptions2() throws IOException {
        String requestMethodOptions2 = "OPTIONS /method_options2 HTTP/1.1\r\nline2\nline3\n";
        InputStream stream = new ByteArrayInputStream(requestMethodOptions2.getBytes());
        InputStreamReader feed = new InputStreamReader(stream);
        BufferedReader in = new BufferedReader(feed);
        HTTPRequest request = new HTTPRequest(in);
        String expectedHeaders = "HTTP/1.1 200 OK\r\nAllow: GET,OPTIONS\r\nDate: bla\r\nContent-Type: text/html\r\n";
        assertEquals(expectedHeaders + "\r\n" + "", new HTTPResponse(request, "bla").get());
    }

    @Test
    void getResponseToGetRequestForPatchContent() throws IOException {
        String requestGetPatchContent = "GET /patch-content.txt HTTP/1.1\r\nline2\nline3\n";
        InputStream stream = new ByteArrayInputStream(requestGetPatchContent.getBytes());
        InputStreamReader feed = new InputStreamReader(stream);
        BufferedReader in = new BufferedReader(feed);
        HTTPRequest request = new HTTPRequest(in);
        String expectedHeaders = "HTTP/1.1 200 OK\r\nDate: bla\r\nContent-Type: text/html\r\n";
        assertEquals(expectedHeaders + "\r\n" + "default content", new HTTPResponse(request, "bla").get());
    }

    @Test
    void getResponseToPatchRequestForPatchContent() throws IOException {
        String requestGetPatchContent =
                "PATCH /patch-content.txt HTTP/1.1\r\nIf-Match: 5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0\r\nline3\r\n";
        InputStream stream = new ByteArrayInputStream(requestGetPatchContent.getBytes());
        InputStreamReader feed = new InputStreamReader(stream);
        BufferedReader in = new BufferedReader(feed);
        HTTPRequest request = new HTTPRequest(in);
        String expectedHeaders = "HTTP/1.1 204\r\nDate: bla\r\nContent-Type: text/html\r\n";
        assertEquals(expectedHeaders + "\r\n", new HTTPResponse(request, "bla").get());
    }
}