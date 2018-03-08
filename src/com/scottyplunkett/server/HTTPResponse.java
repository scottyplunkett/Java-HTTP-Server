package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        int encoded = HTTPResponseCode.encode(route);
        String responseHeaders = buildHeaders(HTTPResponseCode.retrieve(encoded), "text/html");
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

    static String buildHeaders(String status, String contentType) {
        String CRLF = "\r\n";
        String statusLine = "HTTP/1.1 " + status + CRLF;
        String locationLine = "Location: /\r\n";
        String dateLine = "Date: " + getDate() + CRLF;
        String contentTypeLine = "Content-Type: " + contentType + CRLF;
        if ("302 Found".equals(status)) statusLine = statusLine + locationLine;
        return statusLine + dateLine + contentTypeLine;
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
                    setBody(html[0].replace(tag, String.valueOf(key) + " = " + value));
                    html[0] = body;
                }
            });
        }
        return body;
    }
}