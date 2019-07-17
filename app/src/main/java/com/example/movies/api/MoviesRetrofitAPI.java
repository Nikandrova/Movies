package com.example.movies.api;

import com.example.movies.data.MovieResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesRetrofitAPI {
//  /discover/movie?api_key=5d190a4676660309ee5187b997f90f2c&sort_by=popularity.desc&page=1
//   Observable<com.example.movies.data.Movie> getPostersList();

   @GET("movie/popular")
   Single<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

   @GET("movie/top_rated")
   Single<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);
}
