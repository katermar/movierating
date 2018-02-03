package com.katermar.movierating.database.dao;

import com.katermar.movierating.entity.Director;
import com.katermar.movierating.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by katermar on 2/3/2018.
 */
public interface DirectorDao extends GenericDao<Director> {
    Director getDirectorByFilm(int filmId) throws DAOException;

    @Override
    boolean create(Director director) throws DAOException;

    @Override
    Director constructFromResultSet(ResultSet selected) throws SQLException;

    List<Director> getAll() throws DAOException;

    Director getByName(String name) throws DAOException;
}
