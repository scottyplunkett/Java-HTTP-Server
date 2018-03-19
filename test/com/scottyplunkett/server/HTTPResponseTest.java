package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPResponseTest {

        @Test
        void get() throws IOException {
            String expectedHeaders = new HTTPResponseHeaders("200 OK", "text/html", "bla").get();
            String nicoleRequest = "GET /nicole HTTP/1.1";
            Path filePath = Router.route(nicoleRequest);
            String content = Files.lines( filePath ).collect( Collectors.joining() );
            assertEquals(expectedHeaders + "\r\n" + content, new HTTPResponse(nicoleRequest, "bla").get());
        }

        @Test
        void getResponseToRequestWithParams() throws IOException {
            String expectedGeneratedContent = "<p>variable_1 = Scott</p><br><p>variable_2 = Plunkett</p><br>";
            String expectedHeaders = new HTTPResponseHeaders("200 OK", "text/html", "bla").get();
            String requestedWithParams = "GET /pages?variable_1=Scott&variable_2=Plunkett HTTP/1.1";
            Path templatePath = Router.route(requestedWithParams);
            String content = Files.lines( templatePath ).collect( Collectors.joining() );
            content = content.replace("$content", expectedGeneratedContent);
            assertEquals(expectedHeaders + "\r\n" + content, new HTTPResponse(requestedWithParams, "bla").get());
        }
}