package com.scottyplunkett.server;

import java.nio.file.Path;
import java.util.HashMap;

public class DocumentTags {
    HashMap<Path, String> tagMap;

    DocumentTags(){
        tagMap = new HashMap<>();
    }

    void setTag(Path path, String eTag) {
        tagMap.put(path, eTag);
    }

    String getTag(Path path) {
        return tagMap.get(path);
    }
}
