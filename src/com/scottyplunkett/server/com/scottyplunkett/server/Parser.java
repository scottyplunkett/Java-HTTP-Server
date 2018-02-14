package com.scottyplunkett.server;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Parser {
    static Function<String, String[]> splitBySpace = string -> string.split("\\s+");
    static Function<String, String[]> splitByQuestionMark = string -> string.split("\\?");

    public static String parseFor(String parseType, String getRequest) {
        return findRequestMethod(getRequest);
    }

    static String findRequestMethod(String requestLine) {
        return splitBySpace.apply(requestLine)[0];
    }


    static String findRequestedRoute(String requestLine) {
        return splitByQuestionMark.apply(splitBySpace.apply(requestLine)[1])[0];
    }

    public static Map<String, String> mapQuery(String requestLine) {
        Map<String, String> query = new HashMap<>();
        String justQuery = splitByQuestionMark.apply(splitBySpace.apply(requestLine)[1])[1];
        for (String keyValue : justQuery.split("\\&")){
            String[] pairs = keyValue.split("\\=");
            query.put(pairs[0], pairs.length == 1 ? "" : pairs[1]);
        }
        return query;
    }
}
