package com.katermar.movierating.database.dao;

import com.katermar.movierating.entity.Genre;
import com.katermar.movierating.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by katermar on 2/3/2018.
 */
public interface GenreDao extends GenericDao<Genre> {
    List<Genre> getGenresByFilmId(long id) throws DAOException;

    @Override
    boolean create(Genre genre) throws DAOException;

    @Override
    Genre constructFromResultSet(ResultSet selected) throws SQLException;

    List<Genre> getAll() throws DAOException;

    Genre getByName(String genreName) throws DAOException;

    void addGenresForFilm(List<String> genres, int filmId) throws DAOException;

    void deleteByIdFilm(String idFilm) throws DAOException;
}
