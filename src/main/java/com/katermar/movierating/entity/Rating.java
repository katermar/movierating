package com.katermar.movierating.entity;

/**
 * Created by katermar on 12/31/2017.
 */
public class Rating {
    private int id;
    private int idUser;
    private int idFilm;
    private boolean isSeen;
    private int ratingAmount;

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

    public boolean getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(boolean isSeen) {
        this.isSeen = isSeen;
    }

    public int getRatingAmount() {
        return ratingAmount;
    }

    public void setRatingAmount(int ratingAmount) {
        this.ratingAmount = ratingAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;

        if (id != rating.id) return false;
        if (idUser != rating.idUser) return false;
        if (idFilm != rating.idFilm) return false;
        return isSeen == rating.isSeen && ratingAmount == rating.ratingAmount;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idUser;
        result = 31 * result + idFilm;
        result = 31 * result + (isSeen ? 1 : 0);
        result = 31 * result + ratingAmount;
        return result;
    }
}
