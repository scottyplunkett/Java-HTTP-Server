package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPRequestTest {

    @Test
    void testReadReturnsRequestAsArrayList() throws IOException {
        InputStream stream = new ByteArrayInputStream("requestLine\r\nline2\r\nline3\r\nline4".getBytes());
        InputStreamReader feed = new InputStreamReader(stream);
        BufferedReader in = new BufferedReader(feed);
        ArrayList<String> requestList = new ArrayList<>();
        Collections.addAll(requestList, new String[]{"requestLine", "line2", "line3", "line4"});
        assertEquals(requestList, HTTPRequest.read(in));
    }
}