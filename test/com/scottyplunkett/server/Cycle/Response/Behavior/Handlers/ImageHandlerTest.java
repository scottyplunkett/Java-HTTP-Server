package com.scottyplunkett.server.Cycle.Response.Behavior.Handlers;

import com.scottyplunkett.server.Cycle.Request.HTTPRequest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ImageHandlerTest {

    @Test
    void getJPEG() throws IOException {
        Path jpegPath = Paths.get("public/image.jpeg");
        byte[] expectedImage = Files.readAllBytes(jpegPath);
        String requestLine = "GET /image.jpeg HTTP/1.1\r\n";
        InputStream in = new ByteArrayInputStream(requestLine.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        ImageHandler imageHandler = new ImageHandler();
        imageHandler.setHttpRequest(request);
        imageHandler.produceContent();
        assertArrayEquals(expectedImage, imageHandler.getImageData());
    }

    @Test
    void getGIF() throws IOException {
        Path gifPath = Paths.get("public/image.gif");
        byte[] expectedImage = Files.readAllBytes(gifPath);
        String requestLine = "GET /image.gif HTTP/1.1\r\n";
        InputStream in = new ByteArrayInputStream(requestLine.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        ImageHandler imageHandler = new ImageHandler();
        imageHandler.setHttpRequest(request);
        imageHandler.produceContent();
        assertArrayEquals(expectedImage, imageHandler.getImageData());
    }

    @Test
    void getPNG() throws IOException {
        Path pngPath = Paths.get("public/image.png");
        byte[] expectedImage = Files.readAllBytes(pngPath);
        String requestLine = "GET /image.png HTTP/1.1\r\n";
        InputStream in = new ByteArrayInputStream(requestLine.getBytes());
        HTTPRequest request = new HTTPRequest(in);
        ImageHandler imageHandler = new ImageHandler();
        imageHandler.setHttpRequest(request);
        imageHandler.produceContent();
        assertArrayEquals(expectedImage, imageHandler.getImageData());
    }
}