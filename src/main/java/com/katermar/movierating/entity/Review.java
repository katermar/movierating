package com.katermar.movierating.entity;

import java.sql.Timestamp;

/**
 * Created by katermar on 12/31/2017.
 */
public class Review {
    private int idreview;
    private int iduser;
    private int idfilm;
    private String text;
    private Timestamp date;
    private User user;

    public int getIdreview() {
        return idreview;
    }

    public void setIdreview(int idreview) {
        this.idreview = idreview;
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

        if (idreview != review.idreview) return false;
        if (iduser != review.iduser) return false;
        if (idfilm != review.idfilm) return false;
        if (text != null ? !text.equals(review.text) : review.text != null) return false;
        if (date != null ? !date.equals(review.date) : review.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idreview;
        result = 31 * result + iduser;
        result = 31 * result + idfilm;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }


    /**
     * Created by katermar on 1/9/2018.
     */
    public static enum ReviewFields {
        IDREVIEW,
        IDUSER,
        ISFILM,
        TEXT,
        DATE
    }
}
