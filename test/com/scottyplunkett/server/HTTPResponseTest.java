package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static com.scottyplunkett.server.ByteArraysReducer.merge;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class HTTPResponseTest {

    @Test
    void get() throws IOException {
        String nicoleRequest = "GET /nicole HTTP/1.1";
        InputStream in = new ByteArrayInputStream(nicoleRequest.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        Path filePath = Router.route(nicoleRequest);
        String content = Files.lines( filePath ).collect( Collectors.joining() );
        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                                 "Date: bla\r\n" +
                                 "Content-Type: text/html\r\n";
        String expectedResponse = expectedHeaders + "\r\n" + content;
        assertArrayEquals(expectedResponse.getBytes(), new HTTPResponse(request, "bla").get());
    }

    @Test
    void getResponseToRequestWithParams() throws IOException {
        String requestedWithParams = "GET /pages?variable_1=Scott&variable_2=Plunkett HTTP/1.1";
        InputStream in = new ByteArrayInputStream(requestedWithParams.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        Path templatePath = Router.route(requestedWithParams);
        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                                 "Date: bla\r\n" +
                                 "Content-Type: text/html\r\n";
        String expectedGeneratedContent = "<p>variable_1 = Scott</p><br><p>variable_2 = Plunkett</p><br>";
        String content = Files.lines( templatePath ).collect( Collectors.joining() );
        content = content.replace("$content", expectedGeneratedContent);
        String expectedResponse = expectedHeaders + "\r\n" + content;
        assertArrayEquals(expectedResponse.getBytes(), new HTTPResponse(request, "bla").get());
    }

    @Test
    void getResponseToOptionsRequestForMethodOptions() throws IOException {
        String requestMethodOptions = "OPTIONS /method_options HTTP/1.1";
        InputStream in = new ByteArrayInputStream(requestMethodOptions.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                                 "Allow: GET,HEAD,POST,OPTIONS,PUT\r\n" +
                                 "Date: bla\r\n" +
                                 "Content-Type: text/html\r\n";
        String expectedResponse = expectedHeaders + "\r\n" + "";
        assertArrayEquals(expectedResponse.getBytes(), new HTTPResponse(request, "bla").get());
    }

    @Test
    void getResponseToOptionsRequestForMethodOptions2() throws IOException {
        String requestMethodOptions2 = "OPTIONS /method_options2 HTTP/1.1";
        InputStream in = new ByteArrayInputStream(requestMethodOptions2.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        String expectedHeaders = "HTTP/1.1 200 OK\r\n" +
                                 "Allow: GET,OPTIONS\r\n" +
                                 "Date: bla\r\n" +
                                 "Content-Type: text/html\r\n";
        String expectedResponse = expectedHeaders + "\r\n" + "";
        assertArrayEquals(expectedResponse.getBytes(), new HTTPResponse(request, "bla").get());
    }

    @Test
    void getResponseToGetRequestForPatchContent() throws IOException {
        String requestGetPatchContent = "GET /patch-content.txt HTTP/1.1\r\nline2\nline3\n";
        InputStream in = new ByteArrayInputStream(requestGetPatchContent.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        String expectedHeaders = "HTTP/1.1 200 OK\r\nDate: bla\r\nContent-Type: text/html\r\n";
        String expectedResponse = expectedHeaders + "\r\n" + "default content";
        assertArrayEquals(expectedResponse.getBytes(), new HTTPResponse(request, "bla").get());
    }

    @Test
    void getResponseToPatchRequestForPatchContent() throws IOException {
        String requestGetPatchContent =
                "PATCH /patch-content.txt HTTP/1.1\r\nIf-Match: 5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0\r\nline3\r\n";
        InputStream in = new ByteArrayInputStream(requestGetPatchContent.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        String expectedHeaders = "HTTP/1.1 204\r\nDate: bla\r\nContent-Type: text/html\r\n";
        String expectedResponse = expectedHeaders + "\r\n";
        assertArrayEquals(expectedResponse.getBytes(), new HTTPResponse(request, "bla").get());
    }

    @Test
    void getResponseToRequestForImageJPEG() throws IOException {
        String requestGetImageContent = "GET /image.jpeg HTTP/1.1\r\nline 4\r\nline3\r\n";
        InputStream in = new ByteArrayInputStream(requestGetImageContent.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        Path imagePath = Paths.get("public/image.jpeg");
        byte[] body = Files.readAllBytes(imagePath);
        byte[] head = (new HTTPResponseHeaders("200 OK", "image/jpeg", "bla").get() + "\r\n").getBytes();
        byte[] expectedResponse = merge(body, head);
        assertArrayEquals(expectedResponse, new HTTPResponse(request, "bla").get());
    }

    @Test
    void getResponseToRequestForImageGIF() throws IOException {
        String requestGetImageContent = "GET /image.gif HTTP/1.1\r\nline 4\r\nline3\r\n";
        InputStream in = new ByteArrayInputStream(requestGetImageContent.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        Path imagePath = Paths.get("public/image.gif");
        byte[] body = Files.readAllBytes(imagePath);
        byte[] head = (new HTTPResponseHeaders("200 OK", "image/gif", "bla").get() + "\r\n").getBytes();
        byte[] expectedResponse = merge(body, head);
        assertArrayEquals(expectedResponse, new HTTPResponse(request, "bla").get());
    }

    @Test
    void getResponseToRequestForImagePNG() throws IOException {
        String requestGetImageContent = "GET /image.png HTTP/1.1\r\nline 4\r\nline3\r\n";
        InputStream in = new ByteArrayInputStream(requestGetImageContent.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        Path imagePath = Paths.get("public/image.png");
        byte[] body = Files.readAllBytes(imagePath);
        byte[] head = (new HTTPResponseHeaders("200 OK", "image/png", "bla").get() + "\r\n").getBytes();
        byte[] expectedResponse = merge(body, head);
        assertArrayEquals(expectedResponse, new HTTPResponse(request, "bla").get());
    }

}