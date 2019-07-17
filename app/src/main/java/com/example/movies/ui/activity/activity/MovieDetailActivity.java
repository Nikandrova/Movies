package com.example.movies.ui.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movies.R;
import com.example.movies.data.Movie;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class MovieDetailActivity extends AppCompatActivity {
    TextView titleMovie, descriptionMovie, ratingMovie, realiseDateMovie;
    ImageView posterMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_movie);

        Intent intent = getIntent();
        Movie movie = Parcels.unwrap(intent.getParcelableExtra("MOVIES"));

        titleMovie.setText(movie.getTitle());
        descriptionMovie.setText(movie.getOverview());
        ratingMovie.setText(String.valueOf(movie.getVoteAverage()));
        realiseDateMovie.setText(movie.getReleaseDate());


        Picasso.get()
                .load(movie.getPosterPath())
                .into(posterMovie);

//        Intent intent = getIntent();
//        if(intent.hasExtra("")){
//            String poster = getIntent().getExtras().getString("poster_path");
//            String name = getIntent().getExtras().getString("title");
//            String description = getIntent().getExtras().getString("overview");
//            String rating = getIntent().getExtras().getString("vote_average");
//            String date = getIntent().getExtras().getString("realise_date");
//
//            Picasso.get()
//                    .load(poster)
//                    .into(posterMovie);
//
//            titleMovie.setText(name);
//            descriptionMovie.setText(description);
//            ratingMovie.setText(rating);
//            realiseDateMovie.setText(date);
//        } else{
//            Toast.makeText(this, "No Api Data", Toast.LENGTH_SHORT).show();
//        }
    }
}
