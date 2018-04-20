package com.scottyplunkett.server.Cycle.Response.Behavior.Handlers;

import com.scottyplunkett.server.Cycle.Response.Routing.Router;
import com.scottyplunkett.server.Cycle.Utils.Date;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.scottyplunkett.server.Cycle.Utils.ByteArraysReducer.merge;

public class ImageHandler {
    private Path imagePath;
    private String type;
    private byte[] imageData;
    private byte[] imageHeaders;
    private byte[] response;

    ImageHandler(String requestLine) throws IOException {
        this(requestLine, Date.getDate());
    }

    public ImageHandler(String requestLine, String date) throws IOException {
        imagePath = Router.route(requestLine);
        type = Files.probeContentType(imagePath);
        imageData = Files.readAllBytes(imagePath);
        imageHeaders = (new HTTPResponseHeaders("200 OK", type, date).get() + "\r\n").getBytes();
        response = merge(imageData, imageHeaders);
    }

    public byte[] get() {
        return response;
    }

    byte[] getImageData() {
        return imageData;
    }
}
