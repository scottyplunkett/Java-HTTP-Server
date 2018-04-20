package com.scottyplunkett.server.Cycle.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Date {

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        String format = "EEE, dd MMM yyyy HH:mm:ss z";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }
}
