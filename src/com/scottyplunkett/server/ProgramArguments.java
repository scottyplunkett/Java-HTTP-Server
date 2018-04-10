package com.scottyplunkett.server;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

class ProgramArguments {
    private final int firstArg = 0;
    private String[] args;
    private int port;
    private Path root;

    ProgramArguments(String[] _args) {
        args = _args;
        setPort();
        setRoot();
    }



    void setPort() {
        for (int thisFlag = firstArg; thisFlag < args.length; thisFlag++ ) {
            int flagValue = thisFlag + 1;
            if(isPortFlag(args[thisFlag]) && isNumericalPortValue(args[flagValue])) {
                port = Integer.parseInt(args[flagValue]);
            }
        }
    }

    void setRoot() {
        for (int thisFlag = firstArg; thisFlag < args.length; thisFlag++ ) {
            int flagValue = thisFlag + 1;
            if(isDirectoryFlag(args[thisFlag]) && isExistingDirectory(args[flagValue])) {
                root = Paths.get(args[flagValue]);
            }
        }
    }

    private boolean isPortFlag(String arg) {
        return arg.equals("-p");
    }

    private static boolean isNumericalPortValue(String arg) {
        return arg.matches("\\d+");
    }

    private boolean isExistingDirectory(String arg) {
        return new File(arg).isDirectory();
    }

    private boolean isDirectoryFlag(String arg) {
        return arg.equals("-d");
    }

    int getPort() {
        return port;
    }

    Path getRoot() {
        return root;
    }
}
