package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.scottyplunkett.server.ByteArraysReducer.merge;

class ImageContentResponse {
    private Path imagePath;
    private String type;
    private byte[] imageData;
    private byte[] imageHeaders;
    private byte[] response;

    ImageContentResponse(String requestLine) throws IOException {
        this(requestLine, Date.getDate());
    }

    ImageContentResponse(String requestLine, String date) throws IOException {
        imagePath = Router.route(requestLine);
        type = Files.probeContentType(imagePath);
        imageData = Files.readAllBytes(imagePath);
        imageHeaders = (new HTTPResponseHeaders("200 OK", type, date).get() + "\r\n").getBytes();
        response = merge(imageData, imageHeaders);
    }

    byte[] get() {
        return response;
    }

    byte[] getImageData() {
        return imageData;
    }
}