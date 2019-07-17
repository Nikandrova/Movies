package com.example.movies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.movies.api.MoviesAPI;
import com.example.movies.data.MovieResponse;
import com.example.movies.ui.activity.App;
import com.example.movies.views.MovieView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MoviePresenter extends MvpPresenter<MovieView> {

    private Disposable d;

    @Inject
    MoviesAPI api;

    public MoviePresenter() {
        App.getInstance().getAppComponent().inject(this);
    }

    public void loadPopularMovies() {
        d = api.provide()
                .getPopularMovies("5d190a4676660309ee5187b997f90f2c")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) {
                        getViewState().onDataLoaded(movieResponse.getResults());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getViewState().onError(throwable);
                    }
                });
    }

    public void loadHeightRatedMovies() {
        d = api.provide()
                .getTopRatedMovies("5d190a4676660309ee5187b997f90f2c")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) {
                        getViewState().onDataLoaded(movieResponse.getResults());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getViewState().onError(throwable);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (d != null) d.dispose();
    }
}
