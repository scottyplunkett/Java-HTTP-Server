package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaggedDocumentHTTPResponseTest {

    @Test
    void getResponsetoPatchRequestforPatchContent() throws IOException {
        String expectedHeaders = "HTTP/1.1 204 No Content\r\n" +
                                 "Date: bla\r\n" +
                                 "Content-Type: text/html\r\n";
        String inputRequestString = "PATCH /patch-content.txt HTTP/1.1\r\n" +
                                    "If-None-Match: 5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0\r\n" +
                                    "Content-Length: 15\r\n";
        InputStream inputStream = new ByteArrayInputStream(inputRequestString.getBytes());
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader in = new BufferedReader(streamReader);
        HTTPRequest request = new HTTPRequest(in);
        String expectedResponse = expectedHeaders + "\r\n" + "";
        assertEquals(expectedResponse, new TaggedDocumentHTTPResponse(request, new DocumentTags(), "bla").get());
    }

}