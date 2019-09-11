package com.example.movies.repository;

import com.example.movies.api.MoviesRetrofitAPI;
import com.example.movies.data.Movie;
import com.example.movies.data.MovieResponse;
import com.example.movies.data.TrailerMovie;
import com.example.movies.data.TrailerMovieResponce;
import com.example.movies.data.TypeMovie;
import com.example.movies.db.AppExecutors;
import com.example.movies.db.MovieDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class MovieRepository {
    private final MoviesRetrofitAPI moviesRetrofitAPI;
    private final MovieDao movieDao;

    public MovieRepository(MovieDao movieDao, MoviesRetrofitAPI moviesRetrofitAPI) {
        this.movieDao = movieDao;
        this.moviesRetrofitAPI = moviesRetrofitAPI;
    }

    List<Movie> popularityMovies;
    private int lastPagePopularityMovies = 1;

    public Single<List<Movie>> getPopularityMovies(int page) {
        if (popularityMovies == null || popularityMovies.size() == 0) {
            return loadPopularMoviesFromServer(page);
        } else if (page > lastPagePopularityMovies) {
            lastPagePopularityMovies++;
            return loadPopularMoviesFromServer(page);
        } else
            return Single.just(popularityMovies);
    }

    private Single<List<Movie>> loadPopularMoviesFromServer(final int page) {
        return moviesRetrofitAPI
                .getPopularMovies("5d190a4676660309ee5187b997f90f2c", page)
                .map(new Function<MovieResponse, List<Movie>>() {
                    @Override
                    public List<Movie> apply(MovieResponse movieResponse) throws Exception {
                        setPopularityMovies(movieResponse.getResults());
                        return popularityMovies;
                    }
                });
    }

    List<Movie> topMovies;
    private int lastPageTopMovies = 1;

    public Single<List<Movie>> getTopMovies(int page) {
        if (topMovies == null || topMovies.size() == 0) {
            return loadTopMovies(page);
        } else if (page > lastPageTopMovies) {
            lastPageTopMovies++;
            return loadTopMovies(page);
        } else
            return Single.just(topMovies);
    }

    public Single<List<Movie>> loadTopMovies(final int page) {
        return moviesRetrofitAPI
                .getTopRatedMovies("5d190a4676660309ee5187b997f90f2c", page)
                .map(new Function<MovieResponse, List<Movie>>() {
                    @Override
                    public List<Movie> apply(MovieResponse movieResponse) throws Exception {
                        setTopMovies(movieResponse.getResults());
                        return topMovies;
                    }
                });
    }

    List<Movie> favoriteListMovies;

    public Single<List<Movie>> getFavoriteListMovies() {
        if (favoriteListMovies == null || favoriteListMovies.size() == 0) {
            return loadFavMoviesFromDB();
        } else return Single.just(favoriteListMovies);
    }

    public Single<List<Movie>> loadFavMoviesFromDB() {
        return movieDao.loadAllMovie().map(new Function<List<Movie>, List<Movie>>() {
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

    public Single<String> getMovieTrailer(int idMovie) {
        Movie movieFromDB = movieDao.loadMovieFromDB(idMovie);
        if (movieFromDB.getKeyTrailer() == null) {
            return loadMovieTrailer(movieFromDB);
        } else {
            return Single.just(movieFromDB.getKeyTrailer());
        }
    }

    public Single<String> loadMovieTrailer(final Movie movie) {
        return moviesRetrofitAPI
                .getTrailersMovie(String.valueOf(movie.getIdMovie()), "5d190a4676660309ee5187b997f90f2c")
                .map(new Function<TrailerMovieResponce, String>() {
                    @Override
                    public String apply(TrailerMovieResponce trailerMovieResponce) throws Exception {
                        for (TrailerMovie trailerMovie : trailerMovieResponce.getResults()) {
                            if (trailerMovie.getSite().compareTo("YouTube") == 0)
                                if (trailerMovie.getType().compareTo("Trailer") == 0) {
                                    movie.setKeyTrailer(trailerMovie.getKey());
                                    movieDao.update(movie);
                                    return trailerMovie.getKey();
                                }
                        }
                        if (movie.getKeyTrailer() == null) {
                            movie.setKeyTrailer(trailerMovieResponce.getResults().get(0).getKey());
                            movieDao.update(movie);
                            return trailerMovieResponce.getResults().get(0).getKey();
                        }
                        return null;
                    }
                });
    }

    public Single<Movie> loadMovieFromDB(int id) {
        return movieDao.loadMovieById(id);
    }

    public Single<Movie> getFavoriteMovieFromDB(int id) {
        return loadMovieFromDB(id);
    }

    public void saveMovie(final Movie movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                movieDao.insertMovie(movie);
            }
        });
    }

    public void deleteMovie(final Movie movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                movieDao.deleteMovieWithId(movie.getIdMovie());
            }
        });
    }

    public void setPopularityMovies(List<Movie> popularityMovies) {
        addPopularMoviesDB(popularityMovies);
        if (this.popularityMovies == null)
            this.popularityMovies = popularityMovies;
        else
            this.popularityMovies.addAll(popularityMovies);
    }

    private void addPopularMoviesDB(List<Movie> popularityMovies) {
        for (Movie movie : popularityMovies) {
            movie.setType(TypeMovie.POPULARITY);
            Movie movieFromDB = movieDao.loadMovieFromDB(movie.getIdMovie());
            if (movieFromDB != null) {
                checkedMovieDB(movie, movieFromDB);
            } else
                saveMovie(movie);
        }
    }

    private void checkedMovieDB(Movie movie, Movie movieFromDB) {
        if (movieFromDB.getTitle() == null) {
            if (movieFromDB.isFavorite())
                movie.isFavorite(true);
            deleteMovie(movieFromDB);
            saveMovie(movie);
        }
    }

    private void setTopMovies(List<Movie> topMovies) {
        addTopMoviesDB(topMovies);
        if (this.topMovies == null)
            this.topMovies = topMovies;
        else
            this.topMovies.addAll(topMovies);
    }

    private void addTopMoviesDB(List<Movie> topMovies) {
        for (Movie movie : topMovies) {
            movie.setType(TypeMovie.TOP);
            Movie movieFromDB = movieDao.loadMovieFromDB(movie.getIdMovie());
            if (movieFromDB != null) {
                checkedMovieDB(movie, movieFromDB);
            } else
                saveMovie(movie);
        }
    }

    public void addFavoriteToDB(final Movie movie) {
        movie.isFavorite(true);
        movieDao.update(movie);
    }

    public void deleteMovieFromDB(final Movie movie) {
        movie.isFavorite(false);
        movieDao.update(movie);
    }

    public Movie getMovieFromDB(int idMovie){
        return movieDao.loadMovieFromDB(idMovie);
    }
}
