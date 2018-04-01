package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.scottyplunkett.server.ByteArraysReducer.merge;

public class FormContentResponse {
    private final Path formPath = Paths.get("pages/form.html");
    private HTTPRequest httpRequest;
    private String method;
    private byte[] head;
    private byte[] body;
    private byte[] responseContent;


    FormContentResponse(HTTPRequest request, String date) throws IOException {
        httpRequest = request;
        method = Parser.findRequestMethod(httpRequest.getRequestLine());
        if(method.equals("POST") || method.equals("PUT")) writeToForm();
        if(method.equals("DELETE")) deleteFormData();
        head = (new HTTPResponseHeaders("200 OK", "text/html", date).get() + "\r\n").getBytes();
        body = Files.readAllBytes(formPath);
        responseContent = merge(body, head);
    }

    private void writeToForm() throws IOException {
        byte[] formData = ("<h1>" + httpRequest.getBody() + "</h1>").getBytes();
        Files.write(formPath, formData);
    }

    private void deleteFormData() throws IOException {
        Files.write(formPath, "".getBytes());
    }

    byte[] get() {
        return responseContent;
    }
}
