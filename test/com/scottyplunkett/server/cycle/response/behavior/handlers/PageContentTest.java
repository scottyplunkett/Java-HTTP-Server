package com.scottyplunkett.server.cycle.response.behavior.handlers;

import com.scottyplunkett.server.cycle.response.routing.Router;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageContentTest {

    @Test
    void get() throws IOException {
        String nicoleRequest = "GET /nicole HTTP/1.1";
        Path nicolePagePath = Router.route(nicoleRequest);
        String expectedContent =
                "<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <title>NICOLE</title>" +
                        "</head>" +
                        "<body>" +
                        "NICOLE'S PAGE" +
                        "</body>" +
                        "</html>";
        String actualContent = new PageContent(nicolePagePath, nicoleRequest).get();
        assertEquals(expectedContent, actualContent);
    }
}