package com.example.movies.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.movies.data.Movie;
import com.example.movies.repository.MovieRepository;
import com.example.movies.views.MovieDetailView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MovieDetailPresenter extends MvpPresenter<MovieDetailView>{

    MovieRepository repository = MovieRepository.getInstance();
    Disposable disposble;

    public MovieDetailPresenter() {
    }

    public static MovieDetailPresenter getInstance() {
        return new MovieDetailPresenter();
    }

    public void getFavoriteMovie(int id){
        disposble = repository.getFavoriteMovieFromDB(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {
                        getViewState().onFavoriteMovieLoaded(movie);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.fillInStackTrace();
                    }
                });
    }

    public void addFavoriteMovie(Movie movie){
        repository.addFavoriteToDB(movie);
    }

    public void deleteFavoriteMovie(Movie movie){
        repository.deleteMovieFromDB(movie);
    }
}