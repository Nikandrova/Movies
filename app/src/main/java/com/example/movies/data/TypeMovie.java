package com.example.movies.data;

public enum TypeMovie {
    TOP("Top"),
    POPULARITY("Popular");

    private final String text;

    TypeMovie(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}