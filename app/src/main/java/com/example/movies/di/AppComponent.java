package com.example.movies.di;

import com.example.movies.presenters.MoviePresenter;
import com.example.movies.App;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MoviePresenter moviePresenter);

    void inject(App app);

//    void inject(movieDao movieDao);
//
//    void inject(AppDatabase appDatabase);
//
//    void inject(FavoriteMovieRepository repository);
}
