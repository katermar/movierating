package com.katermar.movierating.database.dao.impl;

import com.katermar.movierating.database.connection.ConnectionPool;
import com.katermar.movierating.database.dao.UserDao;
import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.DAOException;

import java.sql.*;
import java.time.Instant;
import java.util.List;

import static com.katermar.movierating.database.dao.UserDao.UserTableFields.BAN_EXPIRATION_DATE;
import static com.katermar.movierating.entity.User.UserRole;
import static com.katermar.movierating.entity.User.UserStatus;

/**
 * Created by katermar on 1/1/2018.
 */
public class UserDaoImpl implements UserDao {
    private static final String USERS_SELECT_LOGIN_PASSWORD = "SELECT * FROM user WHERE login = ? AND password = ?";
    private static final String USERS_SELECT_ALL = "SELECT * FROM user";
    private static final String USERS_SELECT_LOGIN = "SELECT * FROM user WHERE login = ?";
    private static final String USERS_SELECT_EMAIL = "SELECT * FROM user WHERE email = ?";
    private static final String USERS_INSERT_REGISTER = "INSERT INTO user (login, password, email, real_name, date_of_birth) VALUES (?, ?, ?, ?, ?)";
    private static final String USERS_UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE iduser = ?";
    private static final String USERS_UPDATE_STATUS = "UPDATE user SET status = ? WHERE iduser = ?";
    private static final String USERS_UPDATE_EMAIL = "UPDATE user SET ban_expiration_date = ? WHERE iduser = ?";
    private static final String USERS_UPDATE_ROLE = "UPDATE user SET role = ? WHERE iduser = ?";
    private static final String USERS_UPDATE_BAN = "UPDATE user SET email = ? WHERE iduser = ?";

    @Override
    public boolean create(User user) throws DAOException {
        try (Connection connectionFromPool = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connectionFromPool.prepareStatement(USERS_INSERT_REGISTER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRealName());
            statement.setDate(5, user.getDateOfBirth());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public List<User> findAll() throws DAOException {
        return findAll(USERS_SELECT_ALL);
    }

    @Override
    public boolean updatePassword(String password, long userId) throws DAOException {
        return updateParameter(password, userId, USERS_UPDATE_PASSWORD);
    }

    @Override
    public boolean updateEmail(String status, long userId) throws DAOException {
        return updateParameter(status, userId, USERS_UPDATE_EMAIL);
    }

    @Override
    public boolean updateBan(Date date, long userId) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement selectUsersStatement = connection.prepareStatement(USERS_UPDATE_BAN)) {
            selectUsersStatement.setDate(1, date);
            if (date.after(Date.from(Instant.now()))) {
                updateStatus(UserStatus.BANED.name(), userId);
            }
            selectUsersStatement.setLong(2, userId);
            selectUsersStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e); // todo specific message
        }
        return true;
    }

    @Override
    public boolean updateStatus(String status, long userId) throws DAOException {
        return updateParameter(status, userId, USERS_UPDATE_STATUS);
    }

    @Override
    public boolean updateRole(String role, long userId) throws DAOException {
        return updateParameter(role, userId, USERS_UPDATE_ROLE);
    }

    @Override
    public User deleteById(long id) {
        return null;
    }

    @Override
    public User findByLoginAndPassword(String login, String password) throws DAOException {
        User user = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement selectUsersStatement = connection.prepareStatement(USERS_SELECT_LOGIN_PASSWORD)) {
            selectUsersStatement.setString(1, login);
            selectUsersStatement.setString(2, password);

            try (ResultSet selectedUsers = selectUsersStatement.executeQuery()) {
                while (selectedUsers.next()) {
                    user = constructFromResultSet(selectedUsers);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e); // todo specific message
        }
        return user;
    }

    @Override
    public User findByLogin(String login) throws DAOException {
        return findByParameter(login, USERS_SELECT_LOGIN).get(0);
    }

    @Override
    public User findByEmail(String email) throws DAOException {
        return findByParameter(email, USERS_SELECT_EMAIL).get(0);
    }

    private boolean updateParameter(String param, long userId, String statementString) throws DAOException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement selectUsersStatement = connection.prepareStatement(statementString)) {
            selectUsersStatement.setString(1, param);
            selectUsersStatement.setLong(2, userId);
            selectUsersStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e); // todo specific message
        }
        return true;
    }

    @Override
    public User constructFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("iduser"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setEmail(resultSet.getString("email"));
        user.setRealName(resultSet.getString("real_name"));
        user.setDateOfRegistration(resultSet.getTimestamp("date_of_registration"));
        user.setDateOfBirth(resultSet.getDate("date_of_birth"));
        UserRole role = UserRole.valueOf(resultSet.getString("role").toUpperCase());
        user.setRole(role);
        UserStatus status = UserStatus.valueOf(resultSet.getString("status").toUpperCase());
        user.setStatus(status);
        user.setBanExpirationDate(resultSet.getDate(BAN_EXPIRATION_DATE.name().toLowerCase()));
        return user;
    }
}