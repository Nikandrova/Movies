package com.example.movies.db;

import android.app.Application;
import android.app.PendingIntent;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.movies.App;
import com.example.movies.data.Movie;
import com.example.movies.ui.activity.MovieDetailActivity;

@Database(entities = {Movie.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
//    public static AppDatabase getInstance(Context context) {
//        if (sInstance == null) {
//            synchronized (LOCK) {
//                Log.d(LOG_TAG, "Creating new database instance");
//                sInstance = Room.databaseBuilder(context.getApplicationContext(),
//                        AppDatabase.class, AppDatabase.DATABASE_NAME)
//                        .fallbackToDestructiveMigration()
//                        .allowMainThreadQueries()
//                        .build();
//            }
//        }
//        Log.d(LOG_TAG, "Getting the database instance");
//        return sInstance;
//    }

//    public AppDatabase(){
//        App.getInstance().getAppComponent().inject(this);
//    }

    public abstract MovieDao movieDao();
}
