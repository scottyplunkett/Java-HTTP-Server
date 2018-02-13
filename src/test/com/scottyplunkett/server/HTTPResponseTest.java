package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPResponseTest {

    @Test
    void build() throws IOException {
        }
    };

    @Test
    void getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String expectedDate = dateFormat.format(calendar.getTime());
        String actualDate = HTTPResponse.getDate();
        assertEquals(expectedDate, actualDate);
    }

    @Test
    void buildHeaders() {
        assertEquals("HTTP/1.1 200 OK\r\n" +
                        "Date: mockdate\r\n" +
                        "Content-Type: text/html\r\n",
                HTTPResponse.buildHeaders("1.1", "200", "OK",
                        "mockdate",
                        "text/html"));
    }
}