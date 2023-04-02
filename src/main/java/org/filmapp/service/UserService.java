package org.filmapp.service;

import org.filmapp.DatabaseConnection;
import org.filmapp.dto.UserDto;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserService {
    private final Connection connection;

    @Inject
    public UserService(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
    }

    public Optional<UserDto> findById(int id) {
        try {
            String query = "SELECT * FROM users where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                UserDto user = new UserDto();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return Optional.of(user);
            }else return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error getting user", e);
        }
    }
}
