package com.scottyplunkett.server.Cycle.Response.Behavior.handlers;

import com.scottyplunkett.server.Cycle.Request.HTTPRequest;
import com.scottyplunkett.server.Cycle.Response.Routing.Router;
import com.scottyplunkett.server.Cycle.Utils.Date;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.scottyplunkett.server.Cycle.Utils.ByteArraysReducer.merge;

public class ImageHandler extends Handler {
    private HTTPRequest httpRequest;
    private String date;
    private Path imagePath;
    private String type;
    private byte[] imageData;
    private byte[] imageHeaders;
    private byte[] response;

    public ImageHandler() {}

    ImageHandler(HTTPRequest request) throws IOException {
        this(request, Date.getDate());
    }

    ImageHandler(HTTPRequest request, String _date) {
        httpRequest = request;
        date = _date;
    }

    public byte[] get() {
        return response;
    }

    public byte[] getImageData() {
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
