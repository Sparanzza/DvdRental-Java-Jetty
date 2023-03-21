package org.filmapp;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DatabaseModule.class})
public interface AppComponent {
    UserRepository getUserRepository();
    ActorRepository getActorRepository();
}