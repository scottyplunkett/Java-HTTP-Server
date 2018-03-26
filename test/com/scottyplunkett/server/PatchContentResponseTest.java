package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatchContentResponseTest {

    @Test
    void getResponseToGetRequestForPatchContent() throws IOException {
        String requestGetPatchContent = "GET /patch-content.txt HTTP/1.1\r\nline2\nline3\n";
        InputStream stream = new ByteArrayInputStream(requestGetPatchContent.getBytes());
        InputStreamReader feed = new InputStreamReader(stream);
        BufferedReader in = new BufferedReader(feed);
        HTTPRequest request = new HTTPRequest(in);
        String expectedHeaders = "HTTP/1.1 200 OK\r\nDate: bla\r\nContent-Type: text/html\r\n";
        assertEquals(expectedHeaders + "\r\n" + "default content", new PatchContentResponse(request, "bla").get());
    }

    @Test
    void getResponseToPatchRequestForPatchContent() throws IOException {
        String requestGetPatchContent =
                "PATCH /patch-content.txt HTTP/1.1\r\nIf-Match: 5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0\r\nline3\r\n";
        InputStream stream = new ByteArrayInputStream(requestGetPatchContent.getBytes());
        InputStreamReader feed = new InputStreamReader(stream);
        BufferedReader in = new BufferedReader(feed);
        HTTPRequest request = new HTTPRequest(in);
        String expectedHeaders = "HTTP/1.1 204\r\nDate: bla\r\nContent-Type: text/html\r\n";
        assertEquals(expectedHeaders + "\r\n", new PatchContentResponse(request, "bla").get());
    }

    @Test
    void patchesContentWithRequestBody() throws IOException {
        String requestPatchContent =
                "PATCH /patch-content.txt HTTP/1.1\r\n" +
                "If-Match: dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec\r\n" +
                "line3\r\n" +
                "patched content";
        InputStream stream = new ByteArrayInputStream(requestPatchContent.getBytes());
        InputStreamReader feed = new InputStreamReader(stream);
        BufferedReader in = new BufferedReader(feed);
        HTTPRequest request = new HTTPRequest(in);
        PatchContentResponse patchContentResponse = new PatchContentResponse(request, "bla");
        Path patchContentPath = Paths.get("public/patch-content.txt");
        PageContent pageContent = new PageContent(patchContentPath, request.getRequestLine());
        assertEquals("patched content", pageContent.get());

        String secondRequestPatchContent =
                "PATCH /patch-content.txt HTTP/1.1\r\n" +
                "If-Match: 5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0\r\n" +
                "line3\r\n" +
                "default content";
        InputStream stream2 = new ByteArrayInputStream(secondRequestPatchContent.getBytes());
        InputStreamReader feed2 = new InputStreamReader(stream2);
        BufferedReader in2 = new BufferedReader(feed2);
        HTTPRequest request2 = new HTTPRequest(in2);
        PatchContentResponse secondPatchContentResponse = new PatchContentResponse(request2, "bla");
        PageContent pageContent2 = new PageContent(patchContentPath, request2.getRequestLine());
        assertEquals("default content", pageContent2.get());
    }
}