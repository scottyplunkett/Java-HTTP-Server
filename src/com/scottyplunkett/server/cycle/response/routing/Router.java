package com.scottyplunkett.server.cycle.response.routing;

import com.scottyplunkett.server.cycle.utils.Parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.scottyplunkett.server.cycle.utils.Parser.splitByQuestionMark;

public class Router {
    private static Path path;
    private static String requestLine;
    private static String requestedRoute;
    private static final Path publicDir = Paths.get("public");
    private static final Path cookiePath = Paths.get("pages/cookie.html");
    private static final Path dynamicHTML = Paths.get("pages/dynamic.html");

    public static Path route(String _requestLine) {
        requestLine = _requestLine;
        requestedRoute = Parser.findRequestedRoute(requestLine);
        return setPath();
    }

    private static Path setPath() {
        setPathWhenRootRequested();
        setPathWhenFileInPublicDirectory();
        setPathWhenFileInPagesDirectory();
        setPathForCookieRequest();
        setPathForParameterizedRequest();
        return path == null ? noFileFound() : path;
    }

    private static void setPathWhenRootRequested() {
        if (requestedRoute.equals("/")) path = publicDir;
    }

    private static void setPathWhenFileInPublicDirectory() {
        Path fileInPublicDir = Paths.get("public" + requestedRoute);
        if (Files.exists(fileInPublicDir)) path = fileInPublicDir;
    }

    private static void setPathWhenFileInPagesDirectory() {
        Path fileInPagesDir = Paths.get("pages" + requestedRoute + ".html");
        if (Files.exists(fileInPagesDir)) path = fileInPagesDir;
    }

    private static void setPathForCookieRequest() {
        if (containsCookie() && hasParameters()) path = cookiePath;
    }

    private static void setPathForParameterizedRequest() {
        if (!containsCookie() && hasParameters()) path = dynamicHTML;
    }

    private static boolean containsCookie() {
        return requestedRoute.contains("cookie");
    }

    private static Path noFileFound() { return Paths.get("pages/404.html"); }

    private static boolean hasParameters() {
        return splitByQuestionMark.apply(requestLine).length > 1;
    }
}
