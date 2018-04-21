package com.scottyplunkett.server.Cycle.Response.Behavior.Handlers;

public enum HTTPResponseCode {
    OK(200, "200 OK"),
    PARTIAL_CONTENT(206, "206 Partial Content"),
    NO_CONTENT(204, "204 No Content"),
    FOUND(302, "302 Found"),
    BAD_REQUEST(400, "400 Bad Request"),
    UNAUTHORIZED(401, "401 Unauthorized"),
    NOT_FOUND(404, "404 Not Found"),
    NOT_ALLOWED(405, "405 Method Not Allowed"),
    INVALID_RANGE(416, "416 Range Not Satisfiable"),
    CONFLICT(409, "409 Conflict"),
    TEAPOT(418, "418"),
    SERVER_ERROR(500, "500 Internal Server Error");

    private final int code;
    private final String status;

    HTTPResponseCode(int code, String status) {
        this.code = code;
        this.status = status;
    }


    public static String retrieve(int code) {
        String status = "";

        for (HTTPResponseCode encoded : values()) {
            if (encoded.code == code) {
                status = encoded.status;
            }
        }

        return status;
    }

    public static int encode(String route) {
        if (route.equals("/redirect")) return 302;
        if (route.equals("/foobar")) return 404;
        if (route.equals("/coffee")) return 418;
        return 200;
    }
}

