package org.filmapp.modules;

import dagger.Module;
import dagger.Provides;
import org.filmapp.DatabaseConnection;

import javax.inject.Singleton;
import java.sql.SQLException;

@Module
public class DatabaseModule {
    private final String url;
    private final String username;
    private final String password;

    public DatabaseModule(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Provides
    @Singleton
    public DatabaseConnection provideDatabaseConnection() {
        try {
            return new DatabaseConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la conexion a la base de datos ", e);
        }
    }

}