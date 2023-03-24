package org.filmapp;

import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.NanoTime;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.lang.System.Logger.Level.INFO;

class TimingHttpChannelListener implements HttpChannel.Listener {
    private final ConcurrentMap<Request, Long> times = new ConcurrentHashMap<>();

    @Override
    public void onRequestBegin(Request request) {
        times.put(request, NanoTime.now());
    }

    @Override
    public void onComplete(Request request) {
        long begin = times.remove(request);
        long elapsed = NanoTime.since(begin);
        System.getLogger("timing").log(INFO, "Request {0} took {1} ns", request, elapsed);
    }
}