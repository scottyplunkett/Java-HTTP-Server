package com.scottyplunkett.server;

import java.io.File;
import java.nio.file.Path;

class DirectoryContent {
    private String directoryContent;

    DirectoryContent(Path path){
        String directory = String.valueOf(path);
        String content = "";
        String[] files = new File(directory).list();
        for (String name : files) {
            String linkToFile = "<a href=\"" + "/" + name + "\">" + name + "</a><br>";
            content += linkToFile;
        }
        directoryContent = content;
    }

    String get(){
        return directoryContent;
    }
}
