package com.example.movies.di;

import com.example.movies.api.MoviesAPI;

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
}
