package com.scottyplunkett.server;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProgramArgumentsTest {
    private String[] args;
    private ProgramArguments programArguments;

    @Test
    void getPort() {
        args = new String[]{"-p", "4000"};
        programArguments = new ProgramArguments(args);
        assertEquals(4000, programArguments.getPort());
    }

    @Test
    void getRoot() {
        args = new String[]{"-d", "public"};
        programArguments = new ProgramArguments(args);
        assertEquals(Paths.get("public"), programArguments.getRoot());
    }

    @Test
    void getPortWhenBothArgsPresent() {
        args = new String[]{"-p", "4000", "-d", "public"};
        programArguments = new ProgramArguments(args);
        assertEquals(4000, programArguments.getPort());
    }

    @Test
    void getRootWhenBothArgsPresent() {
        args = new String[]{"-d", "pages", "-p", "8888"};
        programArguments = new ProgramArguments(args);
        assertEquals(Paths.get("pages"), programArguments.getRoot());
    }
}