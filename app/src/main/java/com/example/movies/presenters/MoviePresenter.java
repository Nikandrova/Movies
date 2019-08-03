package com.example.movies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.movies.api.MoviesAPI;
import com.example.movies.data.Movie;
import com.example.movies.data.MovieResponse;
import com.example.movies.db.AppDatabase;
import com.example.movies.App;
import com.example.movies.views.MovieView;

import java.util.List;

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

    AppDatabase appDatabase = AppDatabase.getInstance(App.getInstance());
    public MoviePresenter() {
        App.getInstance().getAppComponent().inject(this);
    }

    public void loadPopularMovies() {
        if (d != null) d.dispose();
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
        if (d != null) d.dispose();
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

    public void loadFavoriteMovies(){
        if (d != null) d.dispose();

        d = appDatabase.movieDao().loadAllFavMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        getViewState().onDataLoaded(movies);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.fillInStackTrace();
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (d != null) d.dispose();
    }

}
