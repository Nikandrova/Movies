package com.example.movies.di;

import com.example.movies.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    App mApplication;

    public AppModule(App application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    App providesApplication() {
        return mApplication;
    }
}
