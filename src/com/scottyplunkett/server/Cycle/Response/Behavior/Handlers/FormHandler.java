package com.scottyplunkett.server.Cycle.Response.Behavior.Handlers;

import com.scottyplunkett.server.Cycle.Request.HTTPRequest;
import com.scottyplunkett.server.Cycle.Utils.Parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.scottyplunkett.server.Cycle.Utils.ByteArraysReducer.merge;

public class FormHandler extends Handler {
    private HTTPRequest httpRequest;
    private String date;
    private final Path formPath = Paths.get("pages/form.html");
    private String method;
    private byte[] head;
    private byte[] body;
    private byte[] responseContent;

    public FormHandler() {}

    FormHandler(HTTPRequest request, String _date) {
        httpRequest = request;
        date = _date;
    }

    private void writeToForm() throws IOException {
        byte[] formData = ("<h1>" + httpRequest.getBody() + "</h1>").getBytes();
        Files.write(formPath, formData);
    }

    private void deleteFormData() throws IOException {
        Files.write(formPath, "".getBytes());
    }

    public byte[] get() {
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
        method = Parser.findRequestMethod(httpRequest.getRequestLine());
        writeOnPostOrPut();
        deleteOnDelete();
        head = (new HTTPResponseHeaders("200 OK", "text/html", date).get() + "\r\n").getBytes();
        body = Files.readAllBytes(formPath);
        responseContent = merge(body, head);
    }

    private void deleteOnDelete() throws IOException {
        if(method.equals("DELETE")) deleteFormData();
    }

    private void writeOnPostOrPut() throws IOException {
        if(method.equals("POST") || method.equals("PUT")) writeToForm();
    }
}
