package com.scottyplunkett.server;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

class Query {
    private String[] queryElements;

    public String[] getParams() {
        return queryElements;
    }

    Query(String requested) throws UnsupportedEncodingException {
        String query = requested.split("\\?")[1];
        setQueryElements(query);
    }

    private void setQueryElements(String justQuery) throws UnsupportedEncodingException {
        String[] urlEncodedQueryElements = justQuery.split("\\&");
        sizeElementArray(urlEncodedQueryElements);
        int i = 0;
        for(String queryElement : urlEncodedQueryElements) {
            String decodedElement = decodeQueryElement(queryElement);
            queryElements[i++] = decodedElement;
        }
    }

    private void sizeElementArray(String[] urlEncodedQueryElements) {
        int elementCount = urlEncodedQueryElements.length;
        queryElements = new String[elementCount];
    }

    private String decodeQueryElement(String queryElement) throws UnsupportedEncodingException {
        String[] param = queryElement.split("\\=");
        int key = 0;
        int value = 1;
        String decodedValue = URLDecoder.decode(param[value], "UTF-8");
        return param[key] + " = " + decodedValue;
    }
}

