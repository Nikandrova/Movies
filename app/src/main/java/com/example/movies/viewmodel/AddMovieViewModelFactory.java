package com.example.movies.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider.NewInstanceFactory;

import com.example.movies.repository.AppDatabase;

public class AddMovieViewModelFactory extends NewInstanceFactory {
    private final AppDatabase appDatabase;
    private final int mFavoriteId;

    public AddMovieViewModelFactory(AppDatabase database, int favoriteId) {
        appDatabase = database;
        mFavoriteId = favoriteId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddMovieViewModel(appDatabase, mFavoriteId);
    }
}
