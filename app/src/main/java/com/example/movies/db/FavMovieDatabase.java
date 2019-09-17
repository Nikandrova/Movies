package com.example.movies.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.movies.data.FavMovie;

@Database(entities = FavMovie.class, version = 2, exportSchema = false)
public abstract class FavMovieDatabase  extends RoomDatabase {
    public abstract FavMovieDao FavMovieDao();
}
