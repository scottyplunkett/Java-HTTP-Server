package com.scottyplunkett.server.Cycle.Response.Behavior.Handlers;

import com.scottyplunkett.server.Cycle.Request.HTTPRequest;
import com.scottyplunkett.server.HTTPRequest;
import com.scottyplunkett.server.HTTPResponseHeaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.scottyplunkett.server.ByteArraysReducer.merge;

class PartialHandler {
    private final Path partialContentPath = Paths.get("public/partial_content.txt");

    private HTTPRequest httpRequest;
    private String range;
    private int rangeStart;
    private int rangeEnd;
    private int totalBytes;
    private byte[] head;
    private byte[] fileContents;
    private byte[] body;
    private byte[] responseContent;

    PartialHandler(HTTPRequest request, String date) throws IOException {
        httpRequest = request;
        fileContents = Files.readAllBytes(partialContentPath);
        totalBytes = fileContents.length;
        range = request.getRange();
        String[] splitRange = range.split("\\-");
        if(splitRange.length == 1) {
            rangeStart = Integer.parseInt(splitRange[0]);
            rangeEnd = totalBytes;
        } else if(splitRange[0].equals("")) {
            rangeStart = totalBytes - Integer.parseInt(splitRange[1]);
            rangeEnd = totalBytes;
        } else {
            rangeStart = Integer.parseInt(splitRange[0]);
            rangeEnd = Integer.parseInt(splitRange[1]) + 1;
        }
        String headers = new HTTPResponseHeaders("206 Partial Content", "text/plain", date).get();
        headers = headers + "Content-Range: bytes " + rangeStart + "-" + rangeEnd + "/" + totalBytes + "\r\n";
        headers = headers + "Content-Length: " + (rangeEnd - rangeStart) + "\r\n";
        head = (headers + "\r\n").getBytes();
        body = Arrays.copyOfRange(fileContents, rangeStart, rangeEnd);
        responseContent = merge(body, head);
    }

    byte[] get() {
        return responseContent;
    }
}

