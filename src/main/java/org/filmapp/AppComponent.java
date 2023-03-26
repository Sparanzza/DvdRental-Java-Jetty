package org.filmapp;

import dagger.Component;
import org.filmapp.service.ActorService;
import org.filmapp.service.UserRepository;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DatabaseModule.class})
public interface AppComponent {
    UserRepository getUserRepository();
    ActorService getActorService();
}