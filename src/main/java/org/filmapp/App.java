package org.filmapp;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/dvdrental";
        String username = "postgresUser";
        String password = "MysecretPass$$1";

        DatabaseModule databaseModule = new DatabaseModule(url, username, password);

        AppComponent appComponent = DaggerAppComponent.builder()
                .databaseModule(databaseModule)
                .build();

        ActorRepository actorRepository = appComponent.getActorRepository();
        Actor actor = actorRepository.getActorById(1);
        System.out.println(actor);
    }
}