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
        String responseHeaders = buildHeaders("200", "OK",
                                              getDate(),
                                              "text/html");
        String responseBody = getResponseBodyContent(path);
        return responseHeaders + "\r\n" + responseBody;
    }

    static String getDate() {
        Calendar calendar = Calendar.getInstance();
        String format = "EEE, dd MMM yyyy HH:mm:ss z";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

    static String buildHeaders(String responseCode,
                               String reason,
                               String date,
                               String contentType) {
        String space = " ";
        String CRLF = "\r\n";
        String statusLine = "HTTP/1.1 " + responseCode + space + reason + CRLF;
        String dateLine = "Date: " + date + CRLF;
        String contentTypeLine = "Content-Type: " + contentType + CRLF;
        return  statusLine +
                dateLine +
                contentTypeLine;

    }

    private static String getResponseBodyContent(Path filePath) throws IOException {
        String stringFromFile = Files.lines( filePath ).collect( Collectors.joining() );
        System.out.print(stringFromFile);
        return stringFromFile;
    }

}