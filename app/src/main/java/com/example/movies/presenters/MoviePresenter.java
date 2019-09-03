package com.example.movies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.movies.App;
import com.example.movies.data.Movie;
import com.example.movies.repository.MovieRepository;
import com.example.movies.views.MovieView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MoviePresenter extends MvpPresenter<MovieView> {
    @Inject
    MovieRepository repository;

    Disposable movieDisposble;

    public MoviePresenter() {
        App.getInstance().getAppComponent().inject(this);
    }

    public void loadPopularMovies(int page) {
        movieDisposble = repository
                .getPopularityMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) {
                        getViewState().onPopularLoaded(movies);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.fillInStackTrace();
                    }
                });
    }

    public void loadHeightRatedMovies(int page) {
     movieDisposble = repository.getTopMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        getViewState().onTopLoaded(movies);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.fillInStackTrace();
                    }
                });
    }

    public void loadFavoriteMovies() {
        movieDisposble = repository.getFavoriteListMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        getViewState().onMovieLoadedFromDB(movies);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.fillInStackTrace();
                    }
                });
    }
}
