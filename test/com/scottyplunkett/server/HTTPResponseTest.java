package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                    "200 OK",
                    "text/html",
                    "bla");
            String nicoleRequest = "GET /nicole HTTP/1.1";
            Path filePath = Router.route(nicoleRequest);
            String content = Files.lines( filePath ).collect( Collectors.joining() );
            assertEquals(expectedHeaders + "\r\n" + content, HTTPResponse.build(nicoleRequest, "bla"));
        }

        @Test
        void buildContentFromDirectory() {
            String expectedHTML =
                    "<a href=\"/text-file.txt\">text-file.txt</a><br>" +
                    "<a href=\"/file2\">file2</a><br>" +
                    "<a href=\"/patch-content.txt\">patch-content.txt</a><br>" +
                    "<a href=\"/image.gif\">image.gif</a><br>" +
                    "<a href=\"/image.jpeg\">image.jpeg</a><br>" +
                    "<a href=\"/file1\">file1</a><br>" +
                    "<a href=\"/partial_content.txt\">partial_content.txt</a><br>" +
                    "<a href=\"/image.png\">image.png</a><br>";
            String publicDirectory = String.valueOf(Paths.get("public"));
            assertEquals(expectedHTML, HTTPResponse.buildContentFromDirectory(publicDirectory));
        }

        @Test
        void buildDynamicHTML() throws IOException {
            String expectedHeaders = HTTPResponse.buildHeaders(
                    "200 OK",
                    "text/html",
                    "bla");
            String requestedWithParams = "GET /pages?variable_1=Michael&variable_2=Scarn HTTP/1.1";
            Path templatePath = Router.route(requestedWithParams);
            String content = Files.lines( templatePath ).collect( Collectors.joining() );
            content = content.replace("$variable_1", "variable_1 = Michael");
            content = content.replace("$variable_2", "variable_2 = Scarn");
            assertEquals(expectedHeaders + "\r\n" + content, HTTPResponse.build(requestedWithParams, "bla"));

            String expectedHeaders2 = HTTPResponse.buildHeaders(
                    "200 OK",
                    "text/html",
                    "bla");
            String requestedWithParams2 = "GET /pages?variable_1=Scott&variable_2=Plunkett HTTP/1.1";
            Path templatePath2 = Router.route(requestedWithParams2);
            String content2 = Files.lines( templatePath2 ).collect( Collectors.joining() );
            content2 = content2.replace("$variable_1", "variable_1 = Scott");
            content2 = content2.replace("$variable_2", "variable_2 = Plunkett");
            assertEquals(expectedHeaders2 + "\r\n" + content2, HTTPResponse.build(requestedWithParams2, "bla"));
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
            String date = HTTPResponse.getDate();
            String expectedBuilt =
                    "HTTP/1.1 200 OK\r\n" +
                    "Date: " + date + "\r\n" +
                    "Content-Type: text/html\r\n";
            String actualBuild = HTTPResponse.buildHeaders("200 OK", "text/html", date);
            assertEquals(expectedBuilt, actualBuild);
        }

        @Test
        void build302Headers() {
            String date = HTTPResponse.getDate();
            String expectedBuilt302Headers =
                    "HTTP/1.1 302 Found\r\n" +
                    "Location: /\r\n" +
                    "Date: " + date + "\r\n" +
                    "Content-Type: text/html\r\n";
            String actualBuilt302Headers = HTTPResponse.buildHeaders("302 Found", "text/html", date);
            assertEquals(expectedBuilt302Headers, actualBuilt302Headers);
        }

}