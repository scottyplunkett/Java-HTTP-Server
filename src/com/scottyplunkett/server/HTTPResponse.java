package com.scottyplunkett.server;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

abstract class HTTPResponse {
    static String body;

    static void setBody(String htmlString) {
        body = htmlString;
    }

    static String build(String requested) throws IOException {
        return build(requested, getDate());
    }

    static String build(String requested, String date) throws IOException {
        String route = Parser.findRequestedRoute(requested);
        int encoded = HTTPResponseCode.encode(route);
        String responseHeaders = buildHeaders(HTTPResponseCode.retrieve(encoded), "text/html", date);
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

    static String buildHeaders(String status, String contentType, String date) {
        String CRLF = "\r\n";
        String statusLine = "HTTP/1.1 " + status + CRLF;
        String locationLine = "Location: /\r\n";
        String dateLine = "Date: " + date + CRLF;
        String contentTypeLine = "Content-Type: " + contentType + CRLF;
        if ("302 Found".equals(status)) statusLine = statusLine + locationLine;
        return statusLine + dateLine + contentTypeLine;
    }

    static String getResponseBodyContent(String requested) throws IOException {
        Path filePath = Router.route(requested);
        return generateContent(requested, filePath);
    }

    private static String generateContent(String requested, Path filePath) throws IOException {
        if (Files.isDirectory(filePath)) return buildContentFromDirectory(filePath.toString());
        else return generateContentFromPages(filePath, requested);
    }

    private static String generateContentFromPages(Path pagesPath, String requested) throws IOException {
        String htmlString = Files.lines(pagesPath).collect(Collectors.joining());
        if ("pages/dynamic.html".equals(pagesPath.toString())) {
            buildContentFromQuery(requested, htmlString);
            return body;
        } else return htmlString;
    }


    private static void buildContentFromQuery(String requested, String htmlString) throws UnsupportedEncodingException {
            final String[] html = {htmlString};
            Map query = Parser.mapQuery(requested);
            query.keySet().forEach(key -> {
                String tag = "$" + key.toString();
                if (html[0].contains(tag)) replaceHTMLTagWithKey(html, query, key, tag);
                }
            );
        }

    private static void replaceHTMLTagWithKey(String[] html, Map query, Object key, String tag) {
        String value = String.valueOf(query.get(key));
        setBody(html[0].replace(tag, key + " = " + value));
        html[0] = body;
    }

    static String buildContentFromDirectory(String publicDirectory) {
        String content = "";
        File[] files = new File(publicDirectory).listFiles();
        List<String> names =  Arrays.asList(files).parallelStream()
                                    .map(file -> file.getName())
                                    .collect(Collectors.toList());
        for (String name : names) {
            String linkToFile = "<a href=\"" + "/" + name + "\">" + name + "</a><br>";
            content += linkToFile;
        }
        return content;
    }
}