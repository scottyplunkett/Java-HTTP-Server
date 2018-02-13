package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPResponseTest {

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
        String expectedBuilt =  "HTTP/1.1 200 OK\r\n" +
                                "Date: mockdate\r\n" +
                                "Content-Type: text/html\r\n";
        String actualBuild = HTTPResponse.buildHeaders(
                                "200", "OK",
                                "mockdate",
                                "text/html");
        assertEquals(expectedBuilt, actualBuild);
    }
}