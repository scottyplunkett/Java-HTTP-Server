package com.scottyplunkett.server.cycle.response.behavior.handlers;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPResponseBodyTest {

    @Test
    void getResponseBodyWhenRequestHasQueryParams() throws IOException {
        String expectedHTTPResponseBodyBuild =
                "<html><head>    " +
                        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">    " +
                        "<title>Hello, $first $last!</title></head>" +
                        "<body><h1>    " +
                        "<b>Content:</b><br>    " +
                        "<i><p>variable_1 = Michael</p><br><p>variable_2 = Scarn</p><br></i>" +
                        "</h1></body></html>";
        String requestedWithParams = "GET /pages?variable_1=Michael&variable_2=Scarn HTTP/1.1";
        String actualHTTPResponseBodyBuild = new HTTPResponseBody(requestedWithParams).get();
        assertEquals(expectedHTTPResponseBodyBuild, actualHTTPResponseBodyBuild);
    }

    @Test
    void getDirectoryResponseBodyWhenRootRequested() throws IOException {
        String expectedResponseBody =
                "<a href=\"/text-file.txt\">text-file.txt</a><br>" +
                        "<a href=\"/file2\">file2</a><br>" +
                        "<a href=\"/patch-content.txt\">patch-content.txt</a><br>" +
                        "<a href=\"/image.gif\">image.gif</a><br>" +
                        "<a href=\"/image.jpeg\">image.jpeg</a><br>" +
                        "<a href=\"/file1\">file1</a><br>" +
                        "<a href=\"/partial_content.txt\">partial_content.txt</a><br>" +
                        "<a href=\"/image.png\">image.png</a><br>";
        String rootRequest = "GET / HTTP/1.1";
        String actualResponseBody = new HTTPResponseBody(rootRequest).get();
        assertEquals(expectedResponseBody, actualResponseBody);
    }
}