package com.example.movies.api;

import com.example.movies.data.MovieResponse;
import com.example.movies.data.TrailerMovie;
import com.example.movies.data.TrailerMovieResponce;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesRetrofitAPI {
   @GET("movie/popular")
   Single<MovieResponse> getPopularMovies(@Query("api_key") String apiKey,
                                          @Query("page") int pageIndex);

   @GET("movie/top_rated")
   Single<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey,
                                           @Query("page") int pageIndex);

   @GET("movie/{movie_id}/videos")
   Single<TrailerMovieResponce> getTrailersMovie(@Path("movie_id") String movieId,
                                                 @Query("api_key") String apiKey);
}
