package org.filmapp;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActorRepository {
    private final Connection connection;

    @Inject
    public ActorRepository(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
    }

    public Actor getActorById(int id) {
        try {
            String query = "SELECT actor_id, first_name, last_name FROM actor where actor_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Actor actor = new Actor();
                actor.setId(resultSet.getInt("actor_id"));
                actor.setFirstName(resultSet.getString("first_name"));
                actor.setFirstName(resultSet.getString("last_name"));
                return actor;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el usuario", e);
        }
        return null;
    }
}
