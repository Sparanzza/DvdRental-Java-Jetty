package org.filmapp;

import dagger.Component;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.filmapp.modules.DatabaseModule;
import org.filmapp.modules.ServletModule;
import org.filmapp.service.ActorService;
import org.filmapp.service.UserRepository;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DatabaseModule.class, ServletModule.class})
public interface AppComponent {
    UserRepository getUserRepository();
    ActorService getActorService();
    ServletContextHandler provideServletContextHandler();

}