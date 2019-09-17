package com.example.movies.api;

import retrofit2.Retrofit;

public class MoviesAPI {

    private MoviesRetrofitAPI theMovieDBApi;

    public MoviesAPI(Retrofit retrofit) {
        theMovieDBApi = retrofit.create(MoviesRetrofitAPI.class);
    }

    public MoviesRetrofitAPI provide() {
        return theMovieDBApi;
    }
}
