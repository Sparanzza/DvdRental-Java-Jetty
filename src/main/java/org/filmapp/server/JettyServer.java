package org.filmapp.server;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class JettyServer {
    private Server server;

    public JettyServer(int port) {
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setName("server");

        server = new Server(threadPool);
        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSendServerVersion(true);

        HttpConnectionFactory http11 = new HttpConnectionFactory(httpConfig);
        ProxyConnectionFactory proxy = new ProxyConnectionFactory(http11.getProtocol());
        ServerConnector connector = new ServerConnector(server, proxy, http11);

        //connector.addBean(new TimingHttpChannelListener());
        connector.setPort(port);
        server.addConnector(connector);

    }

    public void start() {
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() throws Exception {
        server.stop();
    }

    public void setHandler(HandlerList handlerList) {
        server.setHandler(handlerList);
    }
}