package org.filmapp.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import static java.lang.System.Logger.Level.WARNING;

public class ApiHandler extends AbstractHandler {
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) {
        baseRequest.setHandled(true);
        System.getLogger("timing").log(WARNING, "/api");
    }
}