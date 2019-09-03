package com.example.movies.di;

import android.content.Context;

import com.example.movies.api.MoviesAPI;
import com.example.movies.api.MoviesRetrofitAPI;
import com.example.movies.db.AppDatabase;
import com.example.movies.db.MovieDao;
import com.example.movies.presenters.MovieDetailPresenter;
import com.example.movies.presenters.MoviePresenter;
import com.example.movies.App;
import com.example.movies.repository.MovieRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DatabaseModule.class})
public interface AppComponent {

    void inject(MoviePresenter moviePresenter);

    void inject(App app);

    //void inject(AppDatabase appDatabase);

    //void inject(MoviesAPI moviesAPI);

    void inject(MovieDetailPresenter movieDetailPresenter);

    void inject(MovieDao movieDao);

    //void inject(MoviesRetrofitAPI moviesRetrofitAPI);

    //void inject(MovieRepository repository);
}
