package org.filmapp.modules;

import dagger.Module;
import dagger.Provides;
import jakarta.servlet.http.HttpServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.filmapp.DatabaseConnection;
import org.filmapp.api.ActorController;
import org.filmapp.api.UserController;
import org.filmapp.service.ActorService;
import org.filmapp.service.UserService;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Module
public class ServletModule {

    private final String contextPath;

    public ServletModule(String contextPath) {
        this.contextPath = contextPath;
    }

    @Provides
    @Singleton
    public ServletContextHandler provideServletContextHandler(List<HttpServlet> controllers) {
        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath(contextPath);
        handler.setDisplayName("api");
        controllers.forEach( c -> handler.addServlet(new ServletHolder(c), "/" + c.getClass().getSimpleName().toLowerCase().replace("controller", "") + "/*"));
        return handler;
    }

    @Provides
    @Singleton
    public List<HttpServlet> provideControllers(ActorController actorController, UserController userController) {
        List<HttpServlet> controllers = new ArrayList<>();
        controllers.add(actorController);
        controllers.add(userController);
        return controllers;
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

    @Provides
    @Singleton
    public UserController provideUserController(UserService userService) {
        return new UserController(userService);
    }

    @Provides
    @Singleton
    public UserService provideUserService(DatabaseConnection databaseConnection) {
        return new UserService(databaseConnection);
    }
}
