package com.example.movies.repository;

import com.example.movies.App;
import com.example.movies.api.MoviesAPI;
import com.example.movies.data.Movie;
import com.example.movies.data.MovieResponse;
import com.example.movies.db.AppDatabase;
import com.example.movies.viewmodel.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class MovieRepository {

    //@Inject
    MoviesAPI api = new MoviesAPI();

    private Disposable d;

    private MovieRepository() {
    }

    public static MovieRepository getInstance() {
        return new MovieRepository();
    }

    AppDatabase appDatabase = AppDatabase.getInstance(App.getInstance());

    List<Movie> popularityMovies;
    List<Movie> topMovies;
    List<Movie> favoriteListMovies;
    Movie favoriteMovie;

    public Single<List<Movie>> getPopularityMovies(int page) {
        int lastpage = 1;
        if (popularityMovies == null || popularityMovies.size() == 0) {
            return loadPopularMoviesFromServer(page);
        } else if(page >= lastpage){
            lastpage = page;
            return  loadPopularMoviesFromServer(page);
        } else
            return Single.just(popularityMovies);
    }

    private Single<List<Movie>> loadPopularMoviesFromServer(final int page) {
        return api.provide()
                .getPopularMovies("5d190a4676660309ee5187b997f90f2c", page)
                .map(new Function<MovieResponse, List<Movie>>() {
                    @Override
                    public List<Movie> apply(MovieResponse movieResponse) throws Exception {
                        if(page == 1)
                            setPopularityMovies(movieResponse.getResults());
                        else
                            addPopularityMovies(movieResponse.getResults());
                        return popularityMovies;
                    }
                });
    }

    public Single<List<Movie>> getTopMovies(int page) {
        int lastpage = 1;
        if (topMovies == null || topMovies.size() == 0) {
            return loadHeightRatedMovies(page);
        } else if (page >= lastpage) {
            lastpage = page;
            return loadHeightRatedMovies(page);
        } else
            return Single.just(topMovies);
    }

    public Single<List<Movie>> loadHeightRatedMovies(final int page) {
        return api.provide()
                .getTopRatedMovies("5d190a4676660309ee5187b997f90f2c", page)
                .map(new Function<MovieResponse, List<Movie>>() {
                    @Override
                    public List<Movie> apply(MovieResponse movieResponse) throws Exception {
                        if(page == 1)
                            setTopMovies(movieResponse.getResults());
                        else
                            addTopMovies(movieResponse.getResults());
                        return topMovies;
                    }
                });
    }

    public Single<List<Movie>> getFavoriteListMovies() {
        if (favoriteListMovies == null || favoriteListMovies.size() == 0){
            return loadMoviesFromDB();
        }
        else return Single.just(favoriteListMovies);
    }

    public Single<List<Movie>> loadMoviesFromDB() {
        return appDatabase.movieDao().loadAllFavMovie().map(new Function<List<Movie>, List<Movie>>() {
            @Override
            public List<Movie> apply(List<Movie> moviesFromDB) throws Exception {
                List<Movie> onlyFav = new ArrayList<>();
                for (Movie movie : moviesFromDB) {
                    if (movie.isFavorite())
                        onlyFav.add(movie);
                }
                return onlyFav;
            }
        });
    }

    public Single<Movie> loadFavoriteMovieFromDB(int id){
        return appDatabase.movieDao().loadMovieById(id);
    }

    public Single<Movie> getFavoriteMovieFromDB(int id){
        return loadFavoriteMovieFromDB(id);
    }

    public void saveMovie(final Movie movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.movieDao().insertMovie(movie);
            }
        });
    }

    public void setPopularityMovies(List<Movie> popularityMovie) {
        this.popularityMovies = popularityMovie;
        for (Movie movie : popularityMovies) {
            movie.setType(0);
            saveMovie(movie);
        }
    }

    public void addPopularityMovies(List<Movie> popularityMovies){
        for (Movie movie : popularityMovies) {
            movie.setType(0);
            saveMovie(movie);
        }
        this.popularityMovies.addAll(popularityMovies);
    }

    private void setTopMovies(List<Movie> topMovie) {
        this.topMovies = topMovie;
        for (Movie movie : topMovie) {
            movie.setType(1);
            saveMovie(movie);
        }
    }

    public  void addTopMovies(List<Movie> topMovies){
        for (Movie movie : topMovies) {
            movie.setType(1);
            saveMovie(movie);
        }
        this.topMovies.addAll(topMovies);
    }

    private void setFavoriteListMovies(List<Movie> favoriteMovie) {
        this.favoriteListMovies = favoriteMovie;
        for (Movie movie : favoriteMovie) {
            movie.isFavorite(true);
        }
    }

    public void addFavoriteToDB(final Movie movie) {
        movie.isFavorite(true);
        appDatabase.movieDao().update(movie);
    }

    public void deleteMovieFromDB(final Movie movie) {
        movie.isFavorite(false);
        appDatabase.movieDao().update(movie);
    }
}
