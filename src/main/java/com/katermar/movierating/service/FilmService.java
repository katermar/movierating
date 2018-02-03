package com.katermar.movierating.service;

import com.katermar.movierating.entity.Film;
import com.katermar.movierating.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Created by katermar on 2/3/2018.
 */
public interface FilmService {
    Map<Film, Double> getFilmRatingMapInDescOrder() throws ServiceException;

    List<Film> getAllFilms(String pageNumber, String filmsPerPage) throws ServiceException;

    int getFilmsAmount() throws ServiceException;

    Film getFilmById(long id) throws ServiceException;

    List<Film> getFilmsByDirector(int directorId) throws ServiceException;

    List<Film> getFilmsByGenre(String genreName) throws ServiceException;

    void addFilm(Film film) throws ServiceException;

    Film getFilmByName(String name) throws ServiceException;

    List<Film> searchFilms(Map<String, String[]> parametersMap, String pageNumber, String filmsPerPage) throws ServiceException;

    int getSearchFilmsAmount(Map<String, String[]> parametersMap) throws ServiceException;

    void deleteById(String id) throws ServiceException;
}
