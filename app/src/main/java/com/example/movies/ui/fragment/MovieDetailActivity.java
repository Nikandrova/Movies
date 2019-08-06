package com.example.movies.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.movies.R;
import com.example.movies.data.Movie;
import com.example.movies.presenters.MovieDetailPresenter;
import com.example.movies.views.MovieDetailView;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends MvpAppCompatActivity implements MovieDetailView {
    private static final String TAG = "DetailActivity";

    ToggleButton btnFavourites;
    ImageButton shareButton;
    TextView titleMovie, descriptionMovie, ratingMovie, realiseDateMovie;
    ImageView posterMovie;

    @InjectPresenter
    MovieDetailPresenter movieDetailPresenter;

    private int idMovie;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_movie);
    }

    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.activity_movie);

        Bundle arguments = getIntent().getExtras();
        idMovie = Integer.parseInt(arguments.get("idMovieDetail").toString());

        btnFavourites = findViewById(R.id.btFavorites);
        shareButton = findViewById(R.id.btShare);
        if(shareButton == null) Log.d(TAG, " " + shareButton + " = shareBTN");
        posterMovie = findViewById(R.id.ivMoviePoster);
        titleMovie = findViewById(R.id.tvTitle);
        descriptionMovie = findViewById(R.id.tvOverview);
        ratingMovie = findViewById(R.id.tvVoteAverage);
        realiseDateMovie = findViewById(R.id.tvReleaseDate);

        movieDetailPresenter.getFavoriteMovie(idMovie);
    }

    @Override
    public void onFavoriteMovieLoaded(final Movie movie) {
        if(movie.isFavorite()){
            btnFavourites.setChecked(true);
        }

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = "Обязательно посмотрю этот фильм";
                String shareSub = "Ого!";
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Поделиться"));
            }
        });

        btnFavourites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    movieDetailPresenter.addFavoriteMovie(movie);
                    Snackbar.make(buttonView, "Added to favorite", Snackbar.LENGTH_SHORT).show();
                } else {
                    movieDetailPresenter.deleteFavoriteMovie(movie);

                    Snackbar.make(buttonView, "Removed from favorite", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        titleMovie.setText(movie.getTitle());
        descriptionMovie.setText(movie.getOverview());
        ratingMovie.setText(String.valueOf(movie.getVoteAverage()));
        realiseDateMovie.setText(movie.getReleaseDate());

        Picasso.get()
                .load(movie.getFullImageUrl())
                .into(posterMovie);
    }
}
