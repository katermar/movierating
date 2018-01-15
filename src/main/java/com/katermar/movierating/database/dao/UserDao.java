package com.katermar.movierating.database.dao;

import com.katermar.movierating.entity.User;
import com.katermar.movierating.exception.DAOException;

import java.sql.Date;
import java.util.List;

/**
 * Created by katermar on 1/8/2018.
 */
public interface UserDao extends GenericDao<User> {
    boolean create(User user) throws DAOException;

    User getById(long id) throws DAOException;

    User update(User entity);

    List<User> getAll() throws DAOException;

    boolean updatePassword(String password, long userId) throws DAOException;

    boolean updateEmail(String status, long userId) throws DAOException;

    boolean updateBan(Date date, long userId) throws DAOException;

    boolean updateStatus(String status, long userId) throws DAOException;

    boolean updateRole(String role, long userId) throws DAOException;

    @Override
    User deleteById(long id);

    User getByLoginAndPassword(String login, String password) throws DAOException;

    User getByLogin(String login) throws DAOException;

    User getByEmail(String email) throws DAOException;

    /**
     * Created by katermar on 1/4/2018.
     */
    enum UserTableFields {
        IDUSER,
        LOGIN,
        PASSWORD,
        EMAIL,
        REAL_NAME,
        DATE_OF_REGISTRATION,
        DATE_OF_BIRTH,
        ROLE,
        STATUS,
        BAN_EXPIRATION_DATE
    }
}
