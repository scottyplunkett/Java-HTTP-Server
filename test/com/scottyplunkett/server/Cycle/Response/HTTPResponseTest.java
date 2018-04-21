package com.scottyplunkett.server.Cycle.Response;

import com.scottyplunkett.server.Cycle.Request.HTTPRequest;
import com.scottyplunkett.server.Cycle.Response.Behavior.Handlers.*;
import com.scottyplunkett.server.Cycle.Response.Routing.Router;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static com.scottyplunkett.server.Cycle.Utils.ByteArraysReducer.merge;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertArrayEquals(expectedResponse.getBytes(), new HTTPResponse(request, "bla", new DefaultHandler()).get());
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
        assertArrayEquals(expectedResponse.getBytes(), new HTTPResponse(request, "bla", new DefaultHandler()).get());
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
        HTTPResponse actualInstance = new HTTPResponse(request, "bla", new DefaultHandler());
        byte[] actualBytes = actualInstance.get();
        String actualResponse = new String(actualBytes);
        assertEquals(expectedResponse, actualResponse);
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
        assertArrayEquals(expectedResponse.getBytes(), new HTTPResponse(request, "bla", new DefaultHandler()).get());
    }

    @Test
    void getResponseToGetRequestForPatchContent() throws IOException {
        String requestGetPatchContent = "GET /patch-content.txt HTTP/1.1\r\nline2\nline3\n";
        InputStream in = new ByteArrayInputStream(requestGetPatchContent.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        String expectedHeaders = "HTTP/1.1 200 OK\r\nDate: bla\r\nContent-Type: text/plain\r\n";
        String expectedResponse = expectedHeaders + "\r\n" + "default content";
        assertArrayEquals(expectedResponse.getBytes(), new HTTPResponse(request, "bla", new PatchHandler()).get());
    }

    @Test
    void getResponseToPatchRequestForPatchContent() throws IOException {
        String requestGetPatchContent =
                "PATCH /patch-content.txt HTTP/1.1\r\nIf-Match: 5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0\r\nline3\r\n";
        InputStream in = new ByteArrayInputStream(requestGetPatchContent.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        String expectedHeaders = "HTTP/1.1 204\r\nDate: bla\r\nContent-Type: text/plain\r\n";
        String expectedResponse = expectedHeaders + "\r\n";
        assertArrayEquals(expectedResponse.getBytes(), new HTTPResponse(request, "bla", new PatchHandler()).get());
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
        assertArrayEquals(expectedResponse, new HTTPResponse(request, "bla", new ImageHandler()).get());
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
        assertArrayEquals(expectedResponse, new HTTPResponse(request, "bla", new ImageHandler()).get());
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
        assertArrayEquals(expectedResponse, new HTTPResponse(request, "bla", new ImageHandler()).get());
    }

    @Test
    void getResponseToRequestForTextFileHasCorrectMediaType() throws IOException {
        String requestTextFile = "GET /text-file.txt HTTP/1.1\r\nline 4\r\nline3\r\n";
        InputStream in = new ByteArrayInputStream(requestTextFile.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        Path textFilePath = Paths.get("public/text-file.txt");
        byte[] body = Files.readAllBytes(textFilePath);
        byte[] head = (new HTTPResponseHeaders("200 OK", "text/plain", "bla").get() + "\r\n").getBytes();
        byte[] expectedResponse = merge(body, head);
        assertArrayEquals(expectedResponse, new HTTPResponse(request, "bla", new DefaultHandler()).get());
    }

    @Test
    void getResponseToRequestForCookieWithChocolateParam() throws IOException {
        String requestCookieWithChocolate = "GET /cookie?type=chocolate HTTP/1.1\r\nline 4\r\nline3\r\n";
        InputStream in = new ByteArrayInputStream(requestCookieWithChocolate.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] body = "Eat".getBytes();
        byte[] head = (new HTTPResponseHeaders("200 OK", "text/html", "bla").get() + "Set-Cookie: chocolate" + "\r\n\r\n").getBytes();
        byte[] expectedResponse = merge(body, head);
        assertArrayEquals(expectedResponse, new HTTPResponse(request, "bla", new CookieHandler()).get());
    }

    @Test
    void getLogsWithoutBasicAuthReturns401ResponseCode() throws IOException {
        String requestLogs = "GET /logs HTTP/1.1\r\nline 4\r\nline3\r\n";
        InputStream in = new ByteArrayInputStream(requestLogs.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] head = (new HTTPResponseHeaders("401 Unauthorized", "text/html", "bla").get() +
                "WWW-Authenticate: Basic realm=\"Logs\"\r\n\r\n").getBytes();
        byte[] body = "401 Unauthorized... Probably Above Your Paygrade.".getBytes();
        byte[] expectedResponse = merge(body, head);
        assertArrayEquals(expectedResponse, new HTTPResponse(request, "bla", new LogsHandler()).get());
    }

    @Test
    void getResponseToPostFormRequestWritesToForm() throws IOException {
        String requestPostToForm = "POST /form HTTP/1.1\r\nContent-Length: 11\r\nline3\r\n\r\ndata=fatcat";
        InputStream in = new ByteArrayInputStream(requestPostToForm.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] head = (new HTTPResponseHeaders("200 OK", "text/html", "bla").get() + "\r\n").getBytes();
        byte[] body = "<h1>data=fatcat</h1>".getBytes();
        byte[] expectedResponse = merge(body, head);
        HTTPResponse httpResponse = new HTTPResponse(request, "bla", new FormHandler());
        byte[] actualResponse = httpResponse.get();
        assertArrayEquals(expectedResponse, actualResponse);
        Files.write(Paths.get("pages/form.html"), "".getBytes());
    }

    @Test
    void getResponseToGetForm() throws IOException {
        String requestGetForm = "GET /form HTTP/1.1\r\nline2\r\nline3\r\n\r\n";
        InputStream in = new ByteArrayInputStream(requestGetForm.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] head = (new HTTPResponseHeaders("200 OK", "text/html", "bla").get() + "\r\n").getBytes();
        byte[] body = "".getBytes();
        byte[] expectedResponse = merge(body, head);
        assertArrayEquals(expectedResponse, new HTTPResponse(request, "bla", new FormHandler()).get());
    }

    @Test
    void getPartialContent() throws IOException {
        String requestPartialContent = "GET /partial_content.txt HTTP/1.1\r\nRange: bytes=0-4\r\nline3\r\n\r\n";
        InputStream in = new ByteArrayInputStream(requestPartialContent.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        String headers = new HTTPResponseHeaders("206 Partial Content", "text/plain", "bla").get();
        headers = headers + "Content-Range: bytes 0-5/77\r\n";
        headers = headers + "Content-Length: 5\r\n";
        byte[] head = (headers + "\r\n").getBytes();
        byte[] body = "This ".getBytes();
        byte[] expectedResponse = merge(body, head);
        assertArrayEquals(expectedResponse, new HTTPResponse(request, "bla", new PartialHandler()).get());
    }

    @Test
    void get405WhenRequestPutForFile1() throws IOException {
        String requestPutFile1 = "PUT /file1 HTTP/1.1\r\nline2\r\nline3\r\n\r\n";
        InputStream in = new ByteArrayInputStream(requestPutFile1.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        String headers = new HTTPResponseHeaders("405 Method Not Allowed", "text/html", "bla").get();
        headers = headers + "Allow: GET\r\n";
        byte[] head = (headers + "\r\n").getBytes();
        byte[] body = "405: Method Not Allowed... Stick to what you're good at.".getBytes();
        byte[] expectedResponse = merge(body, head);
        assertArrayEquals(expectedResponse, new HTTPResponse(request, "bla", new RestrictedMethodHandler()).get());
    }
}