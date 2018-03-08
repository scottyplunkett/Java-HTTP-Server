package com.scottyplunkett.server;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

class Parser {
    static Function<String, String[]> splitBySpace = string -> string.split("\\s+");
    static Function<String, String[]> splitByQuestionMark = string -> string.split("\\?");

    static String parseFor(String parseType, String getRequest) {
        return findRequestMethod(getRequest);
    }

    static String findRequestMethod(String requestLine) {
        return splitBySpace.apply(requestLine)[0];
    }


    static String findRequestedRoute(String requestLine) {
        String requestedRoute = splitByQuestionMark.apply(splitBySpace.apply(requestLine)[1])[0];
        return requestedRoute;
    }

    static Map<String, String> mapQuery(String requestLine) throws UnsupportedEncodingException {
        Map<String, String> query = new HashMap<>();
        String justQuery = splitByQuestionMark.apply(splitBySpace.apply(requestLine)[1])[1];
        for (String keyValue : justQuery.split("\\&")){
            String[] pairs = keyValue.split("\\=");
            String decodedParam = decodeURLSafeString(pairs[1]);
            query.put(pairs[0], pairs.length == 1 ? "" : decodedParam);
        }
        return query;
    }

    static String decodeURLSafeString(String encoded) throws UnsupportedEncodingException {
        return URLDecoder.decode(encoded, "UTF-8");
    }
}
