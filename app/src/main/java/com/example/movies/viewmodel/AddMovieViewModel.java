package com.example.movies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.movies.data.Movie;
import com.example.movies.repository.AppDatabase;

public class AddMovieViewModel extends ViewModel {
    private LiveData<Movie> movieLiveData;

    public AddMovieViewModel(AppDatabase database, int favoriteId) {
        movieLiveData = database.movieDao().loadMovieById(favoriteId);
    }

    public LiveData<Movie> getFavorite() {
        return movieLiveData;
    }
}
