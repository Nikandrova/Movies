package com.example.movies.di;

import com.example.movies.api.MoviesAPI;
import com.example.movies.db.AppDatabase;
import com.example.movies.presenters.MovieDetailPresenter;
import com.example.movies.presenters.MoviePresenter;
import com.example.movies.App;
import com.example.movies.repository.MovieRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MoviePresenter moviePresenter);

    void inject(App app);

    void inject(MoviesAPI moviesAPI);

    void inject(MovieDetailPresenter movieDetailPresenter);

//    void inject(movieDao movieDao);
//
    void inject(MovieRepository repository);
}
