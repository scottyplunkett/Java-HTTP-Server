package com.scottyplunkett.server;

import com.scottyplunkett.server.Cycle.Response.Behavior.Handlers.FormHandler;
import com.scottyplunkett.server.Cycle.HTTPRequest;
import com.scottyplunkett.server.Cycle.HTTPResponseHeaders;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.scottyplunkett.server.Cycle.ByteArraysReducer.merge;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class FormHandlerTest {
    @Test
    void getPost() throws IOException {
        String requestPostToForm = "POST /form HTTP/1.1\r\nContent-Length: 11\r\nline3\r\n\r\ndata=fatcat";
        InputStream in = new ByteArrayInputStream(requestPostToForm.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] head = (new HTTPResponseHeaders("200 OK", "text/html", "bla").get() + "\r\n").getBytes();
        byte[] body = "<h1>data=fatcat</h1>".getBytes();
        byte[] expectedResponse = merge(body, head);
        assertArrayEquals(expectedResponse, new FormHandler(request, "bla").get());
    }

    @Test
    void getPut() throws IOException {
        String requestPutToForm = "PUT /form HTTP/1.1\r\nContent-Length: 11\r\nline3\r\n\r\ndata=healthcliff";
        InputStream in = new ByteArrayInputStream(requestPutToForm.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] head = (new HTTPResponseHeaders("200 OK", "text/html", "bla").get() + "\r\n").getBytes();
        byte[] body = "<h1>data=healthcliff</h1>".getBytes();
        byte[] expectedResponse = merge(body, head);
        assertArrayEquals(expectedResponse, new FormHandler(request, "bla").get());
    }

    @Test
    void getDelete() throws IOException {
        String requestDeleteForm = "DELETE /form HTTP/1.1\r\nContent-Length: 11\r\nline3\r\n\r\ndata=healthcliff";
        InputStream in = new ByteArrayInputStream(requestDeleteForm.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] head = (new HTTPResponseHeaders("200 OK", "text/html", "bla").get() + "\r\n").getBytes();
        byte[] body = "".getBytes();
        byte[] expectedResponse = merge(body, head);
        assertArrayEquals(expectedResponse, new FormHandler(request, "bla").get());
    }

}