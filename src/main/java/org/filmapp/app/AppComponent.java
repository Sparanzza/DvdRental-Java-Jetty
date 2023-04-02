package org.filmapp.app;

import dagger.Component;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.filmapp.modules.DatabaseModule;
import org.filmapp.modules.ServletModule;
import org.filmapp.service.ActorService;
import org.filmapp.service.UserService;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DatabaseModule.class, ServletModule.class})
public interface AppComponent {
    UserService getUserService();
    ActorService getActorService();
    ServletContextHandler provideServletContextHandler();
}