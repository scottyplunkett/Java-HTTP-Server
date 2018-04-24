package com.scottyplunkett.server.cycle.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ByteArraysReducerTest {
    @Test
    void merge() {
        String firstArray = "first array";
        String secondArray = "second array";
        byte[] expectedCombine = (firstArray + secondArray).getBytes();
        byte[] actualCombine = ByteArraysReducer.merge(firstArray.getBytes(), secondArray.getBytes());
        assertEquals(expectedCombine.length, actualCombine.length);
    }
}