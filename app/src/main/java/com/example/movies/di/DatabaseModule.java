package com.example.movies.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.provider.ContactsContract;

import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.movies.api.MoviesAPI;
import com.example.movies.db.AppDatabase;
import com.example.movies.db.MovieDao;
import com.example.movies.presenters.MovieDetailPresenter;
import com.example.movies.presenters.MoviePresenter;
import com.example.movies.repository.MovieRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    private static final String DATABASE_NAME = "favorite";

    private AppDatabase appDatabase;

    public DatabaseModule(Application application) {
        appDatabase = Room.databaseBuilder(application,
                AppDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase() {
        return appDatabase;
    }


    @Provides
    @Singleton
    MovieDao proviseMovieDao(AppDatabase appDatabase) {
        return appDatabase.movieDao();
    }

    @Provides
    @Singleton
    MovieRepository provideMovieRepository(MovieDao movieDao) {
        return new MovieRepository(movieDao);
    }


    @Provides
    @Singleton
    MovieDetailPresenter provideMovieDetailPresenter() {
        return new MovieDetailPresenter();
    }

    @ProvidePresenter
    @Singleton
    MoviePresenter provideMoviePresenter(){
        return new MoviePresenter();
    }

    @Provides
    @Singleton
    MoviesAPI provideMoviesApi() {
        return new MoviesAPI();
    }
}
