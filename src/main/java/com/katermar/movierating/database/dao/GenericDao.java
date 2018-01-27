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

    void deleteById(String id) throws DAOException;

    boolean create(T user) throws DAOException;

    default List<T> getAll(String selectAll) throws DAOException {
        List<T> userList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement selectUsersStatement = connection.prepareStatement(selectAll)) {

            try (ResultSet selectedUsers = selectUsersStatement.executeQuery()) {
                while (selectedUsers.next()) {
                    userList.add(constructFromResultSet(selectedUsers));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return userList;
    }


    default List<T> getByParameter(String statementString, String... param) throws DAOException {
        List<T> ratings = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(statementString)) {
            int counter = 1;
            for (String p : param) {
                statement.setString(counter, p);
                counter++;
            }
            try (ResultSet selected = statement.executeQuery()) {
                while (selected.next()) {
                    ratings.add(constructFromResultSet(selected));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return ratings;
    }

    T constructFromResultSet(ResultSet selectedUsers) throws SQLException;
}
