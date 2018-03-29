package com.scottyplunkett.server;

class ByteArraysReducer {
    static byte[] merge(byte[] body, byte[] head) {
        byte[] combined = new byte[body.length + head.length];
        for (int bite = 0; bite < combined.length; ++bite) {
            combined[bite] = bite < head.length ? head[bite] : body[bite - head.length];
        }
        return combined;
    }
}

