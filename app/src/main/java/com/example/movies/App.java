package com.example.movies;

import android.app.Application;

import com.example.movies.di.AppComponent;
import com.example.movies.di.AppModule;
import com.example.movies.di.DaggerAppComponent;
import com.example.movies.di.DatabaseModule;
import com.example.movies.repository.MovieRepository;

public class App extends Application {

    private static App app;
    AppComponent component;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        initAppComponent();
        component.inject(this);
    }

    public AppComponent getAppComponent() {
        return component;
    }

    private void initAppComponent() {
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(getInstance()))
                .databaseModule(new DatabaseModule(getInstance()))
                .build();
    }

}
