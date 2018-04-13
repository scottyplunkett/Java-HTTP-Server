package com.scottyplunkett.server;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

class ProgramArguments {
    private final int firstArg = 0;
    private String[] args;
    private Stream<String> streamArgs;
    private int port;
    private Path root;

    ProgramArguments(String[] _args) {
        args = _args;
        streamArgs = Arrays.stream(args);
        setPort();
        setRoot();
    }

    int getPort() {
        return port;
    }

    Path getRoot() {
        return root;
    }

    private boolean isPortFlag(String flag) {
        return flag.equals("-p");
    }

    private static boolean isNumericalPortValue(String portArg) {
        return portArg.matches("\\d+");
    }

    private boolean isExistingDirectory(String directoryArg) {
        return new File(directoryArg).isDirectory();
    }

    private boolean isDirectoryFlag(String arg) {
        return arg.equals("-d");
    }

    private void setPort() {
        port = 5000;
        for (int thisFlag = firstArg; thisFlag < args.length; thisFlag++) {
            int flagValue = thisFlag + 1;
            if (isPortFlag(args[thisFlag]) && isNumericalPortValue(args[flagValue]))
                port = Integer.parseInt(args[flagValue]);
        }
    }

    private void setRoot() {
        for (int thisFlag = firstArg; thisFlag < args.length; thisFlag++) {
            int flagValue = thisFlag + 1;
            if (isDirectoryFlag(args[thisFlag]) && isExistingDirectory(args[flagValue]))
                root = Paths.get(args[flagValue]);
        }
    }
}
