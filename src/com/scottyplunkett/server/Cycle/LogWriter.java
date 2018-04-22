package com.scottyplunkett.server.Cycle;

import com.scottyplunkett.server.Cycle.Request.HTTPRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

class LogWriter {
    HTTPRequest httpRequest;
    private String logLine;
    private static final Path logDirectory = Paths.get("logs");
    private static final Path logPath = Paths.get("logs/logs.html");

    LogWriter(HTTPRequest _httpRequest) {
        httpRequest = _httpRequest;
        logLine = makeLoggableHTML(httpRequest.getRequestLine());
    }

    void logRequest() throws IOException {
        boolean isNoLogDirectory = !Files.exists(logDirectory);
        if (isNoLogDirectory) createLogDirectory();
        writeToLog();
    }

    private void createLogDirectory() throws IOException {
        Files.createDirectory(logDirectory);
    }

    private void writeToLog() throws IOException {
        Files.write(logPath, logLine.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private String makeLoggableHTML(String toLog) {
        return "<h1><b>" + getSystemTime() + " : </b>" + toLog + "</h1><br>\r\n";
    }

    private String getSystemTime() {
        return String.valueOf(System.nanoTime());
    }
}