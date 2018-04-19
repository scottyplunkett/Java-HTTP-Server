package com.scottyplunkett.server.Cycle.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.function.Function;

class Parser {
    private static final Function<String, String[]> splitBySpace = string -> string.split("\\s+");
    private static final Function<String, String[]> splitByQuestionMark = string -> string.split("\\?");

    static String findRequestMethod(String requestLine) {
        return splitBySpace.apply(requestLine)[0];
    }


    static String findRequestedRoute(String requestLine) {
        return splitByQuestionMark.apply(splitBySpace.apply(requestLine)[1])[0];
    }

    static String decodeURLSafeString(String encoded) throws UnsupportedEncodingException {
        return URLDecoder.decode(encoded, "UTF-8");
    }
}
