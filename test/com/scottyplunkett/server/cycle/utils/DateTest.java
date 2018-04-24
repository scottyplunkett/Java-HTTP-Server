package com.scottyplunkett.server.cycle.utils;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateTest {

    @Test
    void getDate() {
        String format = "EEE, dd MMM yyyy HH:mm:ss z";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar calendar = Calendar.getInstance();
        String expectedDate = dateFormat.format(calendar.getTime());
        String actualDate = Date.getDate();
        assertEquals(expectedDate, actualDate);
    }
}