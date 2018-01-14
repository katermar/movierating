package com.katermar.movierating.database.dao;

import com.katermar.movierating.database.connection.ConnectionPool;
import com.katermar.movierating.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by katermar on 1/1/2018.
 */
public interface GenericDao<T> {

    T deleteById(long id);

    default List<T> findAll(String selectAll) throws DAOException {
        List<T> userList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement selectUsersStatement = connection.prepareStatement(selectAll)) {

            try (ResultSet selectedUsers = selectUsersStatement.executeQuery()) {
                while (selectedUsers.next()) {
                    userList.add(constructFromResultSet(selectedUsers));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e); // todo specific message
        }
        return userList;
    }


    default List<T> findByParameter(String param, String statementString) throws DAOException {
        List<T> ratings = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(statementString)) {
            statement.setString(1, param);
            try (ResultSet selected = statement.executeQuery()) {
                while (selected.next()) {
                    ratings.add(constructFromResultSet(selected));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e); // todo specific message
        }
        return ratings;
    }

    T constructFromResultSet(ResultSet selectedUsers) throws SQLException;
}
