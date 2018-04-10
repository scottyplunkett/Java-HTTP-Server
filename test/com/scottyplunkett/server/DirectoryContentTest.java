package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectoryContentTest {

    @Test
    void get() throws IOException {
        String expectedHTML =
                "<a href=\"/text-file.txt\">text-file.txt</a><br>" +
                "<a href=\"/file2\">file2</a><br>" +
                "<a href=\"/patch-content.txt\">patch-content.txt</a><br>" +
                "<a href=\"/image.gif\">image.gif</a><br>" +
                "<a href=\"/image.jpeg\">image.jpeg</a><br>" +
                "<a href=\"/file1\">file1</a><br>" +
                "<a href=\"/partial_content.txt\">partial_content.txt</a><br>" +
                "<a href=\"/image.png\">image.png</a><br>";
        assertEquals(expectedHTML, new DirectoryContent(Paths.get("public")).get());
    }
}