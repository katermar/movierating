package com.katermar.movierating.entity;

/**
 * Created by katermar on 12/31/2017.
 */
public class Director {
    private int iddirector;
    private String firstname;
    private String lastname;

    public int getIddirector() {
        return iddirector;
    }

    public void setIddirector(int iddirector) {
        this.iddirector = iddirector;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Director director = (Director) o;

        return iddirector == director.iddirector && (firstname != null ? firstname.equals(director.firstname) : director.firstname == null) && (lastname != null ? lastname.equals(director.lastname) : director.lastname == null);
    }

    @Override
    public int hashCode() {
        int result = iddirector;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }
}
