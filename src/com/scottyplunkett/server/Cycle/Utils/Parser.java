package com.scottyplunkett.server.Cycle.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.function.Function;

public class Parser {
    static final Function<String, String[]> splitBySpace = string -> string.split("\\s+");
    static final Function<String, String[]> splitByQuestionMark = string -> string.split("\\?");

    public static String findRequestMethod(String requestLine) {
        return splitBySpace.apply(requestLine)[0];
    }


    public static String findRequestedRoute(String requestLine) {
        return splitByQuestionMark.apply(splitBySpace.apply(requestLine)[1])[0];
    }

    static String decodeURLSafeString(String encoded) throws UnsupportedEncodingException {
        return URLDecoder.decode(encoded, "UTF-8");
    }
}
