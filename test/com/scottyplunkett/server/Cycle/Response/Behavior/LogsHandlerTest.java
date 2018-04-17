package com.scottyplunkett.server.Cycle.Response.Behavior;

import com.scottyplunkett.server.Cycle.Request.HTTPRequest;
import com.scottyplunkett.server.Cycle.Response.Behavior.handlers.HTTPResponseHeaders;
import com.scottyplunkett.server.Cycle.Response.Behavior.handlers.LogsHandler;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.scottyplunkett.server.Cycle.Utils.ByteArraysReducer.merge;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class LogsHandlerTest {
    @Test
    void getLogsReturns401WithoutAuth() throws IOException {
        String requestLogs = "GET /logs HTTP/1.1\r\nline 4\r\nline3\r\n";
        InputStream in = new ByteArrayInputStream(requestLogs.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] head = (new HTTPResponseHeaders("401 Unauthorized", "text/html", "bla").get() +
                                               "WWW-Authenticate: Basic realm=\"Logs\"\r\n\r\n").getBytes();
        byte[] body = "401 Unauthorized... Probably Above Your Paygrade.".getBytes();
        byte[] expectedResponse = merge(body, head);
        LogsHandler logsHandler = new LogsHandler(request, "bla");
        logsHandler.produceContent();
        assertArrayEquals(expectedResponse, logsHandler.get());
    }

    @Test
    void getLogsReturnsRequestLogsWhenRequestHasCorrectAuth() throws IOException {
        String requestLogs = "GET /logs HTTP/1.1\r\nAuthorization: Basic YWRtaW46aHVudGVyMg==\r\nline3\r\n";
        InputStream in = new ByteArrayInputStream(requestLogs.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        byte[] head = (new HTTPResponseHeaders("200 OK", "text/html", "bla").get() + "\r\n").getBytes();
        byte[] body = Files.readAllBytes(Paths.get("logs/logs.html"));
        byte[] expectedResponse = merge(body, head);
        LogsHandler logsHandler = new LogsHandler(request, "bla");
        logsHandler.produceContent();
        assertArrayEquals(expectedResponse, logsHandler.get());
    }
}