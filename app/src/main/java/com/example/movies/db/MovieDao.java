package com.example.movies.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.movies.data.Movie;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    Single<List<Movie>> loadAllMovie();

    @Query("SELECT * FROM movies WHERE title = :title")
    List<Movie> loadMovieWithTitle(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("DELETE FROM movies WHERE idMovie = :movie_id")
    void deleteMovieWithId(int movie_id);

    @Query("SELECT * FROM movies WHERE idMovie = :movie_id")
    Single<Movie> loadMovieById(int movie_id);

    @Query("SELECT * FROM movies WHERE idMovie = :movie_id")
    Movie loadMovieFromDB(int movie_id);
}
