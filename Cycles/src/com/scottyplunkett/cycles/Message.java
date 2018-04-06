package com.scottyplunkett.cycles;

public interface Message {
    String stringContents = new String();
    byte[] contents = stringContents.getBytes();

    default byte[] get() { return contents; };
    default String getStringContents() { return stringContents; };
}
