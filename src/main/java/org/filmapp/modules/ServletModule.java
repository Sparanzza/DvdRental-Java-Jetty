package org.filmapp.modules;

import dagger.Module;
import dagger.Provides;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.filmapp.DatabaseConnection;
import org.filmapp.api.ActorController;
import org.filmapp.service.ActorService;

import javax.inject.Singleton;

@Module
public class ServletModule {

    private final String contextPath;

    public ServletModule(String contextPath) {
        this.contextPath = contextPath;
    }

    @Provides
    @Singleton
    public ServletContextHandler provideServletContextHandler(ActorController actorController) {
        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath(contextPath);
        handler.setDisplayName("api");
        handler.addServlet(new ServletHolder(actorController), "/actor/*");
        return handler;
    }

    @Provides
    @Singleton
    public ActorController provideActorController(ActorService actorService) {
        return new ActorController(actorService);
    }

    @Provides
    @Singleton
    public ActorService provideActorService(DatabaseConnection databaseConnection) {
        return new ActorService(databaseConnection);
    }
}
