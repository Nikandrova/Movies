package com.example.movies.ui.activity;

import android.app.Application;

import com.example.movies.di.AppComponent;
import com.example.movies.di.AppModule;
import com.example.movies.di.DaggerAppComponent;

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
                .appModule(new AppModule())
//                .roomModule(new RoomModule(this))
                .build();
    }

}
