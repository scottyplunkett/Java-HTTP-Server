package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.scottyplunkett.server.ByteArraysReducer.merge;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class FormContentResponseTest {
    @Test
    void getPost() throws IOException {
        String requestPostToForm = "POST /form HTTP/1.1\r\nContent-Length: 11\r\nline3\r\n\r\ndata=fatcat";
        InputStream in = new ByteArrayInputStream(requestPostToForm.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] head = (new HTTPResponseHeaders("200 OK", "text/html", "bla").get() + "\r\n").getBytes();
        byte[] body = "<h1>data=fatcat</h1>".getBytes();
        byte[] expectedResponse = merge(body, head);
        FormContentResponse formContentResponse = new FormContentResponse();
        formContentResponse.setHttpRequest(request);
        formContentResponse.setDate("bla");
        formContentResponse.produceContent();
        assertArrayEquals(expectedResponse, formContentResponse.get());
    }

    @Test
    void getPut() throws IOException {
        String requestPutToForm = "PUT /form HTTP/1.1\r\nContent-Length: 11\r\nline3\r\n\r\ndata=healthcliff";
        InputStream in = new ByteArrayInputStream(requestPutToForm.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] head = (new HTTPResponseHeaders("200 OK", "text/html", "bla").get() + "\r\n").getBytes();
        byte[] body = "<h1>data=healthcliff</h1>".getBytes();
        byte[] expectedResponse = merge(body, head);
        FormContentResponse formContentResponse = new FormContentResponse();
        formContentResponse.setHttpRequest(request);
        formContentResponse.setDate("bla");
        formContentResponse.produceContent();
        assertArrayEquals(expectedResponse, formContentResponse.get());
    }

    @Test
    void getDelete() throws IOException {
        String requestDeleteForm = "DELETE /form HTTP/1.1\r\nContent-Length: 11\r\nline3\r\n\r\ndata=healthcliff";
        InputStream in = new ByteArrayInputStream(requestDeleteForm.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] head = (new HTTPResponseHeaders("200 OK", "text/html", "bla").get() + "\r\n").getBytes();
        byte[] body = "".getBytes();
        byte[] expectedResponse = merge(body, head);
        FormContentResponse formContentResponse = new FormContentResponse();
        formContentResponse.setHttpRequest(request);
        formContentResponse.setDate("bla");
        formContentResponse.produceContent();
        assertArrayEquals(expectedResponse, formContentResponse.get());
    }

}