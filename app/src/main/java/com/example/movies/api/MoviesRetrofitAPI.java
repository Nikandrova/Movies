package com.example.movies.api;

import com.example.movies.data.MovieResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesRetrofitAPI {
   @GET("movie/popular")
   Single<MovieResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int pageIndex);

   @GET("movie/top_rated")
   Single<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int pageIndex);
}
