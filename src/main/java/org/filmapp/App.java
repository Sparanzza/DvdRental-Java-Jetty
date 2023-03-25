package org.filmapp;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.filmapp.dto.Actor;
import org.filmapp.repositories.ActorRepository;
import org.filmapp.servlets.HelloServlet;
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
        // Create a HandlerList.
        HandlerList handlerList = new HandlerList();

        // Create a ServletContextHandler with contextPath.
        ServletContextHandler servletContextApiHandler = new ServletContextHandler();
        servletContextApiHandler.setContextPath("/api");
        servletContextApiHandler.setDisplayName("api");
        // Add the Servlet implementing the cart functionality to the context.
        servletContextApiHandler.addServlet(HelloServlet.class, "/hello/*");


        // Add as last a DefaultHandler.
        DefaultHandler defaultHandler = new DefaultHandler();
        handlerList.addHandler(contextCollection);
        handlerList.addHandler(defaultHandler);


        // Create a ServletContextHandler with contextPath.
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/");
        servletContextHandler.setDisplayName("main");

        // Add the DefaultServlet to serve static content.
        ServletHolder servletHolder = servletContextHandler.addServlet(DefaultServlet.class, "/");
        // Configure the DefaultServlet with init-parameters.
        servletHolder.setInitParameter("resourceBase", "./src/main/webapp");
        servletHolder.setAsyncSupported(true);

        contextCollection.addHandler(servletContextApiHandler);
        contextCollection.addHandler(servletContextHandler);
        server.setHandler(handlerList);

        try {
            server.start(); // Start the Server it starts accepting connections from clients.
            // server.join(); // Keep the main thread alive while the server is running.
            System.out.println("Started Jetty Server!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}