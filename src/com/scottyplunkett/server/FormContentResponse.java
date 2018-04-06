package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.scottyplunkett.server.ByteArraysReducer.merge;

class FormContentResponse extends Producer {
    private HTTPRequest httpRequest;
    private String date;
    private final Path formPath = Paths.get("pages/form.html");
    private String method;
    private byte[] head;
    private byte[] body;
    private byte[] responseContent;

    FormContentResponse() {}

    FormContentResponse(HTTPRequest request, String date) throws IOException {
        httpRequest = request;
        method = httpRequest.getRequestLine().split("\\s")[0];
        System.out.println(method);
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

    public void setHttpRequest(HTTPRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void produceContent() throws IOException {
        if(method.equals("POST") || method.equals("PUT")) writeToForm();
        if(method.equals("DELETE")) deleteFormData();
        head = (new HTTPResponseHeaders("200 OK", "text/html", date).get() + "\r\n").getBytes();
        body = Files.readAllBytes(formPath);
        responseContent = merge(body, head);
    }
}
