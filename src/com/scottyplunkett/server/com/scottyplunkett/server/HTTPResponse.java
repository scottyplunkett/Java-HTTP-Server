package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

abstract class HTTPResponse {
    static String body;

    public static void setBody(String htmlString) {
        body = htmlString;
    }

    static String build(String requested) throws IOException {
        String route = Parser.findRequestedRoute(requested);
        Path path = Paths.get(route);
        String responseHeaders = buildHeaders("200", "OK",
                                              "text/html");
        String responseBody = getResponseBodyContent(requested);
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
                               String contentType) {
        String space = " ";
        String CRLF = "\r\n";
        String statusLine = "HTTP/1.1 " + responseCode + space + reason + CRLF;
        String dateLine = "Date: " + getDate() + CRLF;
        String contentTypeLine = "Content-Type: " + contentType + CRLF;
        return  statusLine +
                dateLine +
                contentTypeLine;

    }

    static String getResponseBodyContent(String requested) throws IOException {
        Path filePath = Router.route(requested);
        String htmlString = Files.lines( filePath ).collect( Collectors.joining() );
        if(!filePath.endsWith("dynamic.html")) {
            return htmlString;
        } else {
            final String[] html = {htmlString};
            Map query = Parser.mapQuery(requested);
            query.keySet().forEach(key -> {
                String tag = "$" + key.toString();
                if (html[0].contains(tag)) {
                    String value = String.valueOf(query.get(key));
                    setBody(html[0].replace(tag, value));
                    html[0] = body;
                }
            });
        }
        return body;
    }
}