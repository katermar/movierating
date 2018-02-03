package com.katermar.movierating.entity;

import java.sql.Timestamp;

/**
 * Created by katermar on 12/31/2017.
 */
public class Review {
    private int id;
    private int idUser;
    private int idFilm;
    private String text;
    private Timestamp date;
    private User user;
    private Rating rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (id != review.id) return false;
        if (idUser != review.idUser) return false;
        if (idFilm != review.idFilm) return false;
        return (text != null ? text.equals(review.text) : review.text == null) && (date != null ? date.equals(review.date) : review.date == null);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idUser;
        result = 31 * result + idFilm;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }


    /**
     * Created by katermar on 1/9/2018.
     */
    public enum ReviewFields {
        IDREVIEW,
        IDUSER,
        ISFILM,
        TEXT,
        DATE
    }
}
