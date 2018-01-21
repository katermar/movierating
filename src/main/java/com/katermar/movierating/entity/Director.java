package com.katermar.movierating.entity;

/**
 * Created by katermar on 12/31/2017.
 */
public class Director {
    private int iddirector;
    private String name;

    public Director(String name) {
        this.name = name;
    }

    public int getIddirector() {
        return iddirector;
    }

    public void setIddirector(int iddirector) {
        this.iddirector = iddirector;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Director director = (Director) o;

        if (iddirector != director.iddirector) return false;
        return name != null ? name.equals(director.name) : director.name == null;
    }

    @Override
    public int hashCode() {
        int result = iddirector;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
