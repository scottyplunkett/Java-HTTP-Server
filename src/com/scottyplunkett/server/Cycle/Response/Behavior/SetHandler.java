package com.scottyplunkett.server.Cycle.Response.Behavior;

import com.scottyplunkett.server.Cycle.Response.Behavior.Handlers.*;

public enum SetHandler {
        PATCH(new PatchHandler()),
        IMAGE(new ImageHandler()),
        COOKIE(new CookieHandler()),
        LOG(new LogsHandler()),
        FORM(new FormHandler()),
        PARTIAL(new PartialHandler()),
        RESTRICTEDMETHOD(new RestrictedMethodHandler()),
        DEFAULT(new DefaultHandler());

        private final Handler handler;

        SetHandler(Handler _handler) {
                handler = _handler;
        }

        public Handler getHandler() {
                return handler;
        }
}
