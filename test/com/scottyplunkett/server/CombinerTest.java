package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CombinerTest {

    @Test
    void combineByteArrays() {
        String firstArray = "first array";
        String secondArray = "second array";
        byte[] expectedCombine = (firstArray + secondArray).getBytes();
        byte[] actualCombine = Combiner.combineByteArrays(firstArray.getBytes(), secondArray.getBytes());
        assertEquals(expectedCombine.length, actualCombine.length);
    }
}