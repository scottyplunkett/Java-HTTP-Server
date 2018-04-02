package com.scottyplunkett.server;

enum HTTPResponseCode {
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


    static String retrieve(int code) {
        String status = "";

        for (HTTPResponseCode encoded : values()) {
            if (encoded.code == code) {
                status = encoded.status;
            }
        }

        return status;
    }

    public static int encode(String requestLine) {
        String route = Parser.findRequestedRoute(requestLine);
        String method = Parser.findRequestMethod(requestLine);
        String methodsAllowed = AllowableRoutes.getAllowed(route);
        switch (route) {
            case "/redirect": return 302;
            case "/foobar": return 404;
            case "/coffee": return 418;
            default: return methodsAllowed.contains(method) ? 200 : 405;
        }
    }


    private enum AllowableRoutes {
        FILE1("/file1", "GET"),
        TEXTFILE("/text-file.txt", "GET");

        private final String allowed;
        private final String route;

        AllowableRoutes(String requestedRoute, String allowedMethod) {
            this.route = requestedRoute;
            this.allowed = allowedMethod;
        }

        static String getAllowed(String requestedRoute) {
            String allowable = "";
            for (AllowableRoutes routes : values()) {
                if (routes.route == requestedRoute) {
                    allowable = routes.allowed;
                }
            }

            return allowable;
        }


    }
}


