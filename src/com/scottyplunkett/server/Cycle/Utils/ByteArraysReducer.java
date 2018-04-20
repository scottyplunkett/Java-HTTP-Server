package com.scottyplunkett.server.Cycle.Utils;

public class ByteArraysReducer {
    public static byte[] merge(byte[] body, byte[] head) {
        byte[] combined = new byte[body.length + head.length];
        for (int bite = 0; bite < combined.length; ++bite) {
            combined[bite] = bite < head.length ? head[bite] : body[bite - head.length];
        }
        return combined;
    }
}

