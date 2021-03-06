package com.scottyplunkett.server.cycle.response.behavior.handlers;

import com.scottyplunkett.server.cycle.utils.Query;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class PageContent {
    private String pageContent;

    public PageContent(Path pagesPath, String requested) throws IOException {
        String htmlString = Files.lines(pagesPath).collect(Collectors.joining());
        pageContent = "pages/dynamic.html".equals(pagesPath.toString()) ?
                buildContentFromQuery(requested, htmlString) :
                htmlString;
    }

    public String get(){
        return pageContent;
    }

    private String buildContentFromQuery(String requestLine, String htmlString) throws UnsupportedEncodingException {
        String content = "";
        String request = requestLine.split("\\s")[1];
        Query query = new Query(request);
        String[] params = query.getParams();
        for(String param : params) content = content + ("<p>" + param + "</p><br>");
        return insertHTML(htmlString, content);
    }

    private String insertHTML(String html, String insertable) {
        return html.replace("$content", insertable);
    }
}
