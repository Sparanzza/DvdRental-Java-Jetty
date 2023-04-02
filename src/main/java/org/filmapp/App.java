package org.filmapp;

import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.filmapp.app.AppComponent;
import org.filmapp.app.DaggerAppComponent;
import org.filmapp.modules.DatabaseModule;
import org.filmapp.modules.ServletModule;
import org.filmapp.server.JettyServer;
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

        AppComponent appComponent = DaggerAppComponent.builder()
                .servletModule(new ServletModule("/api"))
                .databaseModule(databaseModule).build();

        ServletContextHandler servletContextApiHandler = appComponent.provideServletContextHandler();

        // Create a ContextHandlerCollection to hold contexts.
        ContextHandlerCollection contextCollection = new ContextHandlerCollection();
        // Create a HandlerList.
        HandlerList handlerList = new HandlerList();

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

        var server = new JettyServer(8080);
        server.setHandler(handlerList);
        server.start();

    }
}