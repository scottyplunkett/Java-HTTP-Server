package com.scottyplunkett.server.Cycle.Response.Behavior.Handlers;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class DirectoryContent {
    private String directoryContent;

    DirectoryContent(Path path){
        String directory = String.valueOf(path);
        String content = "";
        File[] files = new File(directory).listFiles();
        List<String> names =  Arrays.asList(files).parallelStream()
                                .map(file -> file.getName())
                                .collect(Collectors.toList());
        for (String name : names) {
            String linkToFile = "<a href=\"" + "/" + name + "\">" + name + "</a><br>";
            content += linkToFile;
        }
        directoryContent = content;
    }

    String get(){
        return directoryContent;
    }
}
