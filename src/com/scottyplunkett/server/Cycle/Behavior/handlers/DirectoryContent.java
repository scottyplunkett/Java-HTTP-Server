package com.scottyplunkett.server.Cycle.Behavior.handlers;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryContent {
    DirectoryStream<Path> directoryStream;
    private String directoryContent;

    public DirectoryContent(Path path) throws IOException {
        String content = "";
        directoryStream = Files.newDirectoryStream(path);
        for (Path file : directoryStream) {
            String linkToFile = getLinkToFile(file);
            content += linkToFile;
        }
        directoryContent = content;
    }

    private String getLinkToFile(Path file) {
        return "<a href=\"" + "/" + file.getFileName() + "\">" + file.getFileName() + "</a><br>";
    }

    public String get(){
        return directoryContent;
    }
}
