package org.example;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private final Connection connection;

    @Inject
    public UserRepository(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
    }

    public User getUserById(int id) {
        try {
            String query = "SELECT actor_id, first_name, last_name FROM actor where actor_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("actor_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setFirstName(resultSet.getString("last_name"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el usuario", e);
        }
        return null;
    }
}
