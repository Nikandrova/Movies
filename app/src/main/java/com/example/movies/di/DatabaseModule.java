package com.example.movies.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.movies.api.MoviesAPI;
import com.example.movies.api.MoviesRetrofitAPI;
import com.example.movies.db.AppDatabase;
import com.example.movies.db.MovieDao;
import com.example.movies.presenters.MovieDetailPresenter;
import com.example.movies.presenters.MoviePresenter;
import com.example.movies.repository.MovieRepository;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DatabaseModule {
    private static final String DATABASE_NAME = "DBMovie";

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
    MovieDao provideMovieDao(AppDatabase appDatabase) {
        return appDatabase.movieDao();
    }

    @Provides
    @Singleton
    MovieRepository provideMovieRepository(MovieDao movieDao, MoviesRetrofitAPI moviesRetrofitAPI) {
        return new MovieRepository(movieDao, moviesRetrofitAPI);
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
