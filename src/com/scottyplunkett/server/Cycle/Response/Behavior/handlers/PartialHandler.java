package com.scottyplunkett.server.Cycle.Response.Behavior.handlers;

import com.scottyplunkett.server.Cycle.Request.HTTPRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.scottyplunkett.server.Cycle.Utils.ByteArraysReducer.merge;

public class PartialHandler extends Handler {
    private final Path partialContentPath = Paths.get("public/partial_content.txt");
    private HTTPRequest httpRequest;
    private String date;
    private String range;
    private int rangeStart;
    private int rangeEnd;
    private int totalBytes;
    private byte[] head;
    private byte[] fileContents;
    private byte[] body;
    private byte[] responseContent;

    public PartialHandler() {}

    public PartialHandler(HTTPRequest request, String date) {
        setHttpRequest(request);
        setDate(date);
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
        fileContents = Files.readAllBytes(partialContentPath);
        totalBytes = fileContents.length;
        range = httpRequest.getRange();
        String[] splitRange = range.split("\\-");
        setRangeBasedOnLocationOfDash(splitRange);
        setHead();
        body = getByteRange();
        responseContent = merge(body, head);
    }

    private byte[] getByteRange() {
        return Arrays.copyOfRange(fileContents, rangeStart, rangeEnd);
    }

    private void setHead() {
        String headers = new HTTPResponseHeaders("206 Partial Content", "text/plain", date).get();
        headers = headers + "Content-Range: bytes " + rangeStart + "-" + rangeEnd + "/" + totalBytes + "\r\n";
        headers = headers + "Content-Length: " + (rangeEnd - rangeStart) + "\r\n";
        head = (headers + "\r\n").getBytes();
    }

    private void setRangeBasedOnLocationOfDash(String[] splitRange) {
        if (singleValueGivenForRange(splitRange)) setRangeWithOffset(splitRange);
        else setRangeWithTwoGivenValues(splitRange);
    }

    private boolean singleValueGivenForRange(String[] splitRange) {
        return (splitRange.length == 1 || splitRange[0].equals(""));
    }

    private void setRangeWithOffset(String[] splitRange) {
        if(splitRange.length == 1) setRangeOffsetFromStart(splitRange);
        else setRangeOffsetFromEnd(splitRange);
    }

    private void setRangeOffsetFromEnd(String[] splitRange) {
        rangeStart = totalBytes - Integer.parseInt(splitRange[1]);
        rangeEnd = totalBytes;
    }

    private void setRangeOffsetFromStart(String[] splitRange) {
        rangeStart = Integer.parseInt(splitRange[0]);
        rangeEnd = totalBytes;
    }

    private void setRangeWithTwoGivenValues(String[] splitRange) {
        rangeStart = Integer.parseInt(splitRange[0]);
        rangeEnd = Integer.parseInt(splitRange[1]) + 1;
    }
}

