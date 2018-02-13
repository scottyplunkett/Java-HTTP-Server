package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class HTTPResponse {
    public static String build(Path path) throws IOException {
        String responseHeaders = buildHeaders("1.1","200", "OK", getDate(), "text/html");
        String responseBody = getResponseBodyContent(path);
        return responseHeaders + "\r\n" + responseBody;
    }

    static String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

    static String buildHeaders(String httpVersion,
                               String responseCode,
                               String reason,
                               String date,
                               String contentType) {
        return  "HTTP/" + httpVersion + " " + responseCode + " " + reason + "\r\n" +
                "Date: " + date + "\r\n" +
                "Content-Type: " + contentType + "\r\n";
    }

    private static String getResponseBodyContent(Path filePath) throws IOException {
        String stringFromFile = Files.lines( filePath ).collect( Collectors.joining() );
        System.out.print(stringFromFile);
        return stringFromFile;
    }

}