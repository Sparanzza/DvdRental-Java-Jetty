package org.filmapp.service;

import org.filmapp.DatabaseConnection;
import org.filmapp.dto.ActorDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
@Singleton
public class ActorService {
    private final Connection connection;

    @Inject
    public ActorService(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
    }

    public Optional<ActorDto> findById(int id) {
        try {
            String query = "SELECT actor_id, first_name, last_name FROM actor where actor_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ActorDto actor = new ActorDto();
                actor.setId(resultSet.getInt("actor_id"));
                actor.setFirstName(resultSet.getString("first_name"));
                actor.setFirstName(resultSet.getString("last_name"));
                return Optional.of(actor);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting Actor", e);
        }
    }
}
