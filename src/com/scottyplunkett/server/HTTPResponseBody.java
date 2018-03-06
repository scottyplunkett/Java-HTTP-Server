package com.scottyplunkett.server;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HTTPResponseBody {
    static String getResponseBodyContent(String requested) throws IOException {
        Path filePath = Router.route(requested);
        return generateContent(requested, filePath);
    }

    private static String generateContent(String requested, Path filePath) throws IOException {
        return Files.isDirectory(filePath) ? buildContentFromDirectory(filePath.toString()) :
                                             generateContentFromPages(filePath, requested);
    }

    private static String generateContentFromPages(Path pagesPath, String requested) throws IOException {
        String htmlString = Files.lines(pagesPath).collect(Collectors.joining());
        if ("pages/dynamic.html".equals(pagesPath.toString())) {
            buildContentFromQuery(requested, htmlString);
            return HTTPResponse.body;
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
        HTTPResponse.setBody(html[0].replace(tag, key + " = " + value));
        html[0] = HTTPResponse.body;
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
