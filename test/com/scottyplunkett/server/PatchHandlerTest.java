package com.scottyplunkett.server;

import com.scottyplunkett.server.Cycle.HTTPRequest;
import com.scottyplunkett.server.Cycle.PageContent;
import com.scottyplunkett.server.Cycle.PatchHandler;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatchHandlerTest {

    @Test
    void getResponseToGetRequestForPatchContent() throws IOException {
        String requestGetPatchContent = "GET /patch-content.txt HTTP/1.1\r\nline2\nline3\n";
        InputStream in = new ByteArrayInputStream(requestGetPatchContent.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        String expectedHeaders = "HTTP/1.1 200 OK\r\nDate: bla\r\nContent-Type: text/plain\r\n";
        assertEquals(expectedHeaders + "\r\n" + "default content", new PatchHandler(request, "bla").get());
    }

    @Test
    void getResponseToPatchRequestForPatchContent() throws IOException {
        String requestGetPatchContent =
                "PATCH /patch-content.txt HTTP/1.1\r\nIf-Match: 5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0\r\nline3\r\n";
        InputStream in = new ByteArrayInputStream(requestGetPatchContent.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        String expectedHeaders = "HTTP/1.1 204\r\nDate: bla\r\nContent-Type: text/plain\r\n";
        assertEquals(expectedHeaders + "\r\n", new PatchHandler(request, "bla").get());
    }

    @Test
    void patchesContentWithRequestBody() throws IOException {
        String requestPatchContent =
                "PATCH /patch-content.txt HTTP/1.1\r\n" +
                "If-Match: dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec\r\n" +
                "line3\r\n" +
                "patched content";
        InputStream in = new ByteArrayInputStream(requestPatchContent.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        PatchHandler patchHandler = new PatchHandler(request, "bla");
        Path patchContentPath = Paths.get("public/patch-content.txt");
        PageContent pageContent = new PageContent(patchContentPath, request.getRequestLine());
        assertEquals("patched content", pageContent.get());

        String secondRequestPatchContent =
                "PATCH /patch-content.txt HTTP/1.1\r\n" +
                "If-Match: 5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0\r\n" +
                "line3\r\n" +
                "default content";
        InputStream in2 = new ByteArrayInputStream(secondRequestPatchContent.getBytes());
        HTTPRequest request2 = new HTTPRequest(in2);
        PatchHandler secondPatchHandler = new PatchHandler(request2, "bla");
        PageContent pageContent2 = new PageContent(patchContentPath, request2.getRequestLine());
        assertEquals("default content", pageContent2.get());
    }
}