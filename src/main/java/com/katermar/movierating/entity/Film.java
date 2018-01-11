package com.katermar.movierating.entity;

import java.sql.Date;

/**
 * Created by katermar on 1/8/2018.
 */
public class Film {
    private int idfilm;
    private String name;
    private double duration;
    private Date releaseYear;
    private String poster;
    private int iddirector;

    public int getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(int idfilm) {
        this.idfilm = idfilm;
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

    public Date getReleaseYear() {
        return releaseYear;
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

    public int getIddirector() {
        return iddirector;
    }

    public void setIddirector(int iddirector) {
        this.iddirector = iddirector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        if (idfilm != film.idfilm) return false;
        if (Double.compare(film.duration, duration) != 0) return false;
        if (iddirector != film.iddirector) return false;
        if (name != null ? !name.equals(film.name) : film.name != null) return false;
        if (releaseYear != null ? !releaseYear.equals(film.releaseYear) : film.releaseYear != null) return false;
        return poster != null ? poster.equals(film.poster) : film.poster == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idfilm;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(duration);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (releaseYear != null ? releaseYear.hashCode() : 0);
        result = 31 * result + (poster != null ? poster.hashCode() : 0);
        result = 31 * result + iddirector;
        return result;
    }
}