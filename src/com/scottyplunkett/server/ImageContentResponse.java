package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.scottyplunkett.server.ByteArraysReducer.merge;

class ImageContentResponse extends Producer {
    private HTTPRequest httpRequest;
    private String date;
    private Path imagePath;
    private String type;
    private byte[] imageData;
    private byte[] imageHeaders;
    private byte[] response;

    ImageContentResponse() {}

    ImageContentResponse(HTTPRequest request) throws IOException {
        this(request, Date.getDate());
    }

    ImageContentResponse(HTTPRequest request, String _date) throws IOException {
        httpRequest = request;
        date = _date;
    }

    byte[] get() {
        return response;
    }

    byte[] getImageData() {
        return imageData;
    }

    public void setHttpRequest(HTTPRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void produceContent() throws IOException {
        imagePath = Router.route(httpRequest.getRequestLine());
        type = Files.probeContentType(imagePath);
        imageData = Files.readAllBytes(imagePath);
        imageHeaders = (new HTTPResponseHeaders("200 OK", type, date).get() + "\r\n").getBytes();
        response = merge(imageData, imageHeaders);
    }
}
