package org.filmapp;

import dagger.Component;
import org.filmapp.repositories.ActorRepository;
import org.filmapp.repositories.UserRepository;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DatabaseModule.class})
public interface AppComponent {
    UserRepository getUserRepository();
    ActorRepository getActorRepository();
}