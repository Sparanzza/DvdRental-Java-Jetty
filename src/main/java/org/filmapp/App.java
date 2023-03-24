package org.filmapp;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.filmapp.dto.Actor;
import org.filmapp.handler.ApiHandler;
import org.filmapp.handler.AppHandler;
import org.filmapp.repositories.ActorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger L = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        // Database connectino
        String url = "jdbc:postgresql://localhost:5432/dvdrental";
        String username = "postgresUser";
        String password = "MysecretPass$$1";

        DatabaseModule databaseModule = new DatabaseModule(url, username, password);

        AppComponent appComponent = DaggerAppComponent.builder().databaseModule(databaseModule).build();

        ActorRepository actorRepository = appComponent.getActorRepository();
        Actor actor = actorRepository.getActorById(1);
        System.out.println(actor);

        // Create Server Jetty

        // Create and configure a ThreadPool.
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setName("server");

        Server server = new Server(threadPool); // Create a Server instance.
        HttpConfiguration httpConfig = new HttpConfiguration(); // The HTTP configuration object.
        httpConfig.setSendServerVersion(true); // Configure the HTTP support

        HttpConnectionFactory http11 = new HttpConnectionFactory(httpConfig);                   // The ConnectionFactory for HTTP/1.1.
        ProxyConnectionFactory proxy = new ProxyConnectionFactory(http11.getProtocol());        // The ConnectionFactory for the PROXY protocol.
        ServerConnector connector = new ServerConnector(server, proxy, http11);                 // Create a ServerConnector to accept connections from clients.

        // Add the HttpChannel.Listener as bean to the connector.
        connector.addBean(new TimingHttpChannelListener());
        connector.setPort(8080);                                                                // Create the ServerConnector.
        server.addConnector(connector);                                                         // Add the Connector to the Server

        // Create a ContextHandlerCollection to hold contexts.
        ContextHandlerCollection contextCollection = new ContextHandlerCollection();
        server.setHandler(contextCollection);

        // Create a ContextHandler with contextPath.
        ContextHandler appContext = new ContextHandler("/app");
        appContext.setHandler(new AppHandler());
        contextCollection.addHandler(appContext);

        ContextHandler apiContext = new ContextHandler("/api");
        apiContext.setHandler(new ApiHandler());
        contextCollection.addHandler(apiContext);

        contextCollection.deployHandler(apiContext, Callback.NOOP);

        try {
            server.start(); // Start the Server it starts accepting connections from clients.
            // server.join(); // Keep the main thread alive while the server is running.
            System.out.println("Started Jetty Server!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}