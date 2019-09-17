package com.example.movies.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.movies.api.MoviesRetrofitAPI;
import com.example.movies.db.AppDatabase;
import com.example.movies.db.FavMovieDao;
import com.example.movies.db.FavMovieDatabase;
import com.example.movies.db.MovieDao;
import com.example.movies.presenters.MovieDetailPresenter;
import com.example.movies.presenters.MoviePresenter;
import com.example.movies.repository.MovieRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    private static final String DATABASE_NAME = "DBMovie";
    private static final String FAV_DATABASE_NAME = "FavMovieDB";

    private AppDatabase appDatabase;
    private FavMovieDatabase favMovieDatabase;

    public DatabaseModule(Application application) {
        appDatabase = Room.databaseBuilder(application,
                AppDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        favMovieDatabase = Room.databaseBuilder(application,
                FavMovieDatabase.class, FAV_DATABASE_NAME)
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
    FavMovieDatabase provideFavMovieDatabase(){return favMovieDatabase;}


    @Provides
    @Singleton
    MovieDao provideMovieDao(AppDatabase appDatabase) {
        return appDatabase.movieDao();
    }

    @Provides
    @Singleton
    FavMovieDao provideFavMovieDao(FavMovieDatabase favMovieDatabase){
        return favMovieDatabase.FavMovieDao();
    }

    @Provides
    @Singleton
    MovieRepository provideMovieRepository(MovieDao movieDao, FavMovieDao favMovieDao, MoviesRetrofitAPI moviesRetrofitAPI) {
        return new MovieRepository(movieDao, favMovieDao, moviesRetrofitAPI);
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
}
