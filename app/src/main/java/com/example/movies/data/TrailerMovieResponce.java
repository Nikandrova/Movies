package com.example.movies.data;

import java.util.ArrayList;
import java.util.List;

public class TrailerMovieResponce {
    private int id;
    private List<TrailerMovie> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TrailerMovie> getResults() {
        return results;
    }

    public void setResults(List<TrailerMovie> results) {
        this.results = results;
    }
}
