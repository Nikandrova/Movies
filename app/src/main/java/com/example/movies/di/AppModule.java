package com.example.movies.di;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;

import com.example.movies.api.MoviesAPI;
import com.example.movies.db.AppDatabase;
import com.example.movies.db.MovieDao;
import com.example.movies.presenters.MovieDetailPresenter;
import com.example.movies.repository.MovieRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    MoviesAPI provideMoviesApi() {
        return new MoviesAPI();
    }

    @Provides
    MovieRepository provideMovieRepository(){return new MovieRepository();}

    @Provides
    @Singleton
    MovieDetailPresenter provideMovieDetailPresenter(){return new MovieDetailPresenter();}
}
