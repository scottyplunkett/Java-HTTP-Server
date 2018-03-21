package com.scottyplunkett.server;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class TaggedDocumentHTTPResponse extends HTTPResponse {
    String dateString;

    TaggedDocumentHTTPResponse(HTTPRequest request, DocumentTags tags) throws IOException {
        super(request.getRequestLine());
    }

    TaggedDocumentHTTPResponse(HTTPRequest request, DocumentTags tags, String date) throws IOException {
        super(request.getRequestLine());
        dateString = date;
    }

    @Override
    String get() {
        return new HTTPResponseHeaders("204 No Content", "text/html", dateString).get() + "\r\n";
    }

    static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
