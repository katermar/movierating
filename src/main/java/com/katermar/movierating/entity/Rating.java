package com.katermar.movierating.entity;

/**
 * Created by katermar on 12/31/2017.
 */
public class Rating {
    private int idrating;
    private int iduser;
    private int idfilm;
    private boolean isSeen;
    private int ratingAmount;

    public int getIdrating() {
        return idrating;
    }

    public void setIdrating(int idrating) {
        this.idrating = idrating;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(int idfilm) {
        this.idfilm = idfilm;
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

        if (idrating != rating.idrating) return false;
        if (iduser != rating.iduser) return false;
        if (idfilm != rating.idfilm) return false;
        if (isSeen != rating.isSeen) return false;
        return ratingAmount == rating.ratingAmount;
    }

    @Override
    public int hashCode() {
        int result = idrating;
        result = 31 * result + iduser;
        result = 31 * result + idfilm;
        result = 31 * result + (isSeen ? 1 : 0);
        result = 31 * result + ratingAmount;
        return result;
    }
}
