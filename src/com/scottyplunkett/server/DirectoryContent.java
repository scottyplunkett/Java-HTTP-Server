package com.scottyplunkett.server;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

class DirectoryContent {
    DirectoryStream<Path> directoryStream;
    private String directoryContent;

    DirectoryContent(Path path) throws IOException {
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

    String get(){
        return directoryContent;
    }
}
