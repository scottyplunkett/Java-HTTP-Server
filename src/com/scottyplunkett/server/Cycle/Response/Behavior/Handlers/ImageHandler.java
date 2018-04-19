package com.scottyplunkett.server.Cycle.Response.Behavior.Handlers;

import com.scottyplunkett.server.Cycle.Date;
import com.scottyplunkett.server.Cycle.Router;
import com.scottyplunkett.server.Date;
import com.scottyplunkett.server.HTTPResponseHeaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.scottyplunkett.server.ByteArraysReducer.merge;

class ImageHandler {
    private Path imagePath;
    private String type;
    private byte[] imageData;
    private byte[] imageHeaders;
    private byte[] response;

    ImageHandler(String requestLine) throws IOException {
        this(requestLine, Date.getDate());
    }

    ImageHandler(String requestLine, String date) throws IOException {
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