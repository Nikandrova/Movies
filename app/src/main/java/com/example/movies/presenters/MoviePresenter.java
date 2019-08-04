package com.example.movies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.movies.data.Movie;
import com.example.movies.repository.MovieRepository;
import com.example.movies.views.MovieView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MoviePresenter extends MvpPresenter<MovieView> {

    MovieRepository repository = MovieRepository.getInstance();
    Disposable MovieDisposble;

    public MoviePresenter() {
        //App.getInstance().getAppComponent().inject(this);
    }

    public static MoviePresenter getInstance() {
        return new MoviePresenter();
    }

    public void loadPopularMovies() {
        MovieDisposble = repository
                .getPopularityMovies()
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

    public void loadHeightRatedMovies() {
        MovieDisposble = repository.getTopMovies()
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
        MovieDisposble = repository.getFavoriteListMovies()
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
