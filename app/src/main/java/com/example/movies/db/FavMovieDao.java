package com.example.movies.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.movies.data.FavMovie;
import com.example.movies.data.Movie;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface FavMovieDao {
    @Query("SELECT * FROM favMovies")
    Single<List<FavMovie>> loadAllMovie();

    @Query("SELECT * FROM favMovies WHERE title = :title")
    List<FavMovie> loadMovieWithTitle(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(FavMovie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(FavMovie movie);

    @Delete
    void deleteMovie(FavMovie movie);

    @Query("DELETE FROM favMovies WHERE idMovie = :movie_id")
    void deleteMovieWithId(int movie_id);

    @Query("SELECT * FROM favMovies WHERE idMovie = :movie_id")
    Single<FavMovie> loadMovieById(int movie_id);

    @Query("SELECT * FROM favMovies WHERE idMovie = :movie_id")
    FavMovie loadMovieFromDB(int movie_id);
}
