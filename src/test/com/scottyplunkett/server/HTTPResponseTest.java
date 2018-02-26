package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPResponseTest {

        @Test
        void build() throws IOException {
            String expectedHeaders = HTTPResponse.buildHeaders(
                    "200", "OK",
                    "text/html");
            String nicoleRequest = "GET /nicole HTTP/1.1";
            Path filePath = Router.route(nicoleRequest);
            String content = Files.lines( filePath ).collect( Collectors.joining() );
            assertEquals(expectedHeaders + "\r\n" + content, HTTPResponse.build(nicoleRequest));
        }

        @Test
        void buildDynamicHTML() throws IOException {
            String expectedHeaders = HTTPResponse.buildHeaders(
                    "200", "OK",
                    "text/html");
            String requestedWithParams = "GET /pages?first=Michael&last=Scarn HTTP/1.1";
            Path templatePath = Router.route(requestedWithParams);
            String content = Files.lines( templatePath ).collect( Collectors.joining() );
            content = content.replace("$first", "Michael");
            content = content.replace("$last", "Scarn");
            assertEquals(expectedHeaders + "\r\n" + content, HTTPResponse.build(requestedWithParams));

            String expectedHeaders2 = HTTPResponse.buildHeaders(
                    "200", "OK",
                    "text/html");
            String requestedWithParams2 = "GET /pages?first=Scott&last=Plunkett HTTP/1.1";
            Path templatePath2 = Router.route(requestedWithParams2);
            String content2 = Files.lines( templatePath2 ).collect( Collectors.joining() );
            content2 = content2.replace("$first", "Scott");
            content2 = content2.replace("$last", "Plunkett");
            assertEquals(expectedHeaders2 + "\r\n" + content2, HTTPResponse.build(requestedWithParams2));
        }

        @Test

        void getDate() {
            Calendar calendar = Calendar.getInstance();
            String format = "EEE, dd MMM yyyy HH:mm:ss z";
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            String expectedDate = dateFormat.format(calendar.getTime());
            String actualDate = HTTPResponse.getDate();
            assertEquals(expectedDate, actualDate);
        }

        @Test
        void buildHeaders() {
            String expectedBuilt =
                    "HTTP/1.1 200 OK\r\n" +
                    "Date: " + HTTPResponse.getDate() + "\r\n" +
                    "Content-Type: text/html\r\n";
            String actualBuild = HTTPResponse.buildHeaders(
                    "200", "OK",
                    "text/html");
            assertEquals(expectedBuilt, actualBuild);
        }
}