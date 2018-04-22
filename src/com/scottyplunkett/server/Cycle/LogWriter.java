package com.scottyplunkett.server.Cycle;

import com.scottyplunkett.server.Cycle.Request.HTTPRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogWriter {
    private static final Path logPath = Paths.get("Logs/logs.html");

    static void logRequest(HTTPRequest httpRequest) throws IOException {
        String logLine = makeLoggableHTML(httpRequest.getRequestLine());
        Files.write(logPath, logLine.getBytes());
    }

    private static String makeLoggableHTML(String toLog) {
        return "<h1><b>" + getSystemTime() + " : </b>" + toLog + "</h1><br>\r\n";
    }

    private static String getSystemTime() {
        return String.valueOf(System.nanoTime());
    }
}