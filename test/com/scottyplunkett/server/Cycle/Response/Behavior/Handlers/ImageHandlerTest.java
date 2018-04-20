package com.scottyplunkett.server;

import com.scottyplunkett.server.Cycle.ImageHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ImageHandlerTest {

    @Test
    void getJPEG() throws IOException {
        Path jpegPath = Paths.get("public/image.jpeg");
        byte[] expectedImage = Files.readAllBytes(jpegPath);
        String requestLine = "GET /image.jpeg HTTP/1.1";
        assertArrayEquals(expectedImage, new ImageHandler(requestLine).getImageData());
    }

    @Test
    void getGIF() throws IOException {
        Path gifPath = Paths.get("public/image.gif");
        byte[] expectedImage = Files.readAllBytes(gifPath);
        String requestLine = "GET /image.gif HTTP/1.1";
        assertArrayEquals(expectedImage, new ImageHandler(requestLine).getImageData());
    }

    @Test
    void getPNG() throws IOException {
        Path pngPath = Paths.get("public/image.png");
        byte[] expectedImage = Files.readAllBytes(pngPath);
        String requestLine = "GET /image.png HTTP/1.1";
        assertArrayEquals(expectedImage, new ImageHandler(requestLine).getImageData());
    }
}