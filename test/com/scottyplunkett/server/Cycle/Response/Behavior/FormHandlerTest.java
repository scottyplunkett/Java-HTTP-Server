package com.scottyplunkett.server.Cycle.Response.Behavior;

import com.scottyplunkett.server.Cycle.Request.HTTPRequest;
import com.scottyplunkett.server.Cycle.Response.Behavior.handlers.FormHandler;
import com.scottyplunkett.server.Cycle.Response.Behavior.handlers.HTTPResponseHeaders;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.scottyplunkett.server.Cycle.Utils.ByteArraysReducer.merge;
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
        FormHandler formHandler = new FormHandler();
        formHandler.setHttpRequest(request);
        formHandler.setDate("bla");
        formHandler.produceContent();
        assertArrayEquals(expectedResponse, formHandler.get());
    }

    @Test
    void getPut() throws IOException {
        String requestPutToForm = "PUT /form HTTP/1.1\r\nContent-Length: 11\r\nline3\r\n\r\ndata=healthcliff";
        InputStream in = new ByteArrayInputStream(requestPutToForm.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] head = (new HTTPResponseHeaders("200 OK", "text/html", "bla").get() + "\r\n").getBytes();
        byte[] body = "<h1>data=healthcliff</h1>".getBytes();
        byte[] expectedResponse = merge(body, head);
        FormHandler formHandler = new FormHandler();
        formHandler.setHttpRequest(request);
        formHandler.setDate("bla");
        formHandler.produceContent();
        assertArrayEquals(expectedResponse, formHandler.get());
    }

    @Test
    void getDelete() throws IOException {
        String requestDeleteForm = "DELETE /form HTTP/1.1\r\nContent-Length: 11\r\nline3\r\n\r\ndata=healthcliff";
        InputStream in = new ByteArrayInputStream(requestDeleteForm.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] head = (new HTTPResponseHeaders("200 OK", "text/html", "bla").get() + "\r\n").getBytes();
        byte[] body = "".getBytes();
        byte[] expectedResponse = merge(body, head);
        FormHandler formHandler = new FormHandler();
        formHandler.setHttpRequest(request);
        formHandler.setDate("bla");
        formHandler.produceContent();
        assertArrayEquals(expectedResponse, formHandler.get());
    }

}