package org.example;

import org.postgresql.PGConnection;

import java.sql.*;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/dvdrental";
        String username = "postgres";
        String password = "mysecret$$1";

        DatabaseModule databaseModule = new DatabaseModule(url, username, password);

        AppComponent appComponent = DaggerAppComponent.builder()
                .databaseModule(databaseModule)
                .build();

        UserRepository userRepository = appComponent.getUserRepository();
        User user = userRepository.getUserById(1);
        System.out.println(user);
    }
}

