package com.scottyplunkett.server.Cycle.Response.Behavior.handlers;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryContent {
    private DirectoryStream<Path> directoryStream;
    private String directoryContent;
    private static final String fileAnchor = "<a href=\"/$FileName\">$FileName</a><br>";

    public DirectoryContent(Path path) throws IOException {
        String content = "";
        directoryStream = Files.newDirectoryStream(path);
        for (Path file : directoryStream) {
            String linkToFile = buildLink(file);
            content += linkToFile;
        }
        directoryContent = content;
    }

    private String buildLink(Path file) {
        return fileAnchor.replace("$FileName", file.getFileName().toString());
    }

    public String get(){
        return directoryContent;
    }
}
