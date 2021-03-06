package com.katermar.movierating.entity;

import java.sql.Date;
import java.util.List;

/**
 * Created by katermar on 1/8/2018.
 */
public class Film {
    private int id;
    private String name;
    private double duration;
    private Date releaseYear;
    private String poster;
    private String description;
    private int idDirector;
    private Director director;
    private List<Genre> genres;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getReleaseYear() {
        return releaseYear.toLocalDate().getYear();
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        if (id != film.id) return false;
        if (Double.compare(film.duration, duration) != 0) return false;
        if (idDirector != film.idDirector) return false;
        if (name != null ? !name.equals(film.name) : film.name != null) return false;
        if (releaseYear != null ? !releaseYear.equals(film.releaseYear) : film.releaseYear != null) return false;
        return (poster != null ? poster.equals(film.poster) : film.poster == null) && (description != null ? description.equals(film.description) : film.description == null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(duration);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (releaseYear != null ? releaseYear.hashCode() : 0);
        result = 31 * result + (poster != null ? poster.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + idDirector;
        return result;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", releaseYear=" + releaseYear +
                ", poster='" + poster + '\'' +
                ", description='" + description + '\'' +
                ", idDirector=" + idDirector +
                ", director=" + director +
                '}';
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}