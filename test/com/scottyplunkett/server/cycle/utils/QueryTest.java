package com.scottyplunkett.server.cycle.utils;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class QueryTest {

    @Test
    void getParamsWithValues() throws UnsupportedEncodingException {
        Query query = new Query("/pages?variable_1=Michael&variable_2=Scarn");
        String[] actualQueryList = query.getParams();
        String[] expectedQueryList = new String[]{"variable_1 = Michael","variable_2 = Scarn"};
        assertArrayEquals(expectedQueryList, actualQueryList);
    }
}