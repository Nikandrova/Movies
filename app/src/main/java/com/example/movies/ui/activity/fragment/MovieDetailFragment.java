package com.example.movies.ui.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.movies.R;
import com.example.movies.data.Movie;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class MovieDetailFragment extends MvpAppCompatFragment {

    private static final String MOVIES_KEY = "MOVIES";

    public static MovieDetailFragment create(Movie movie) {

        Bundle args = new Bundle();
        args.putParcelable(MOVIES_KEY, Parcels.wrap(movie));
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        TextView titleMovie, descriptionMovie, ratingMovie, realiseDateMovie;
        ImageView posterMovie;

        Button shareButton = getActivity().findViewById(R.id.bShare);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = "Обязательно осмотрю этот фильм";
                String shareSub = "Ого!";
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Поделиться"));
            }
        });

        posterMovie = getActivity().findViewById(R.id.ivMoviePoster);
        titleMovie = getActivity().findViewById(R.id.tvTitle);
        descriptionMovie = getActivity().findViewById(R.id.tvOverview);
        ratingMovie = getActivity().findViewById(R.id.tvVoteAverage);
        realiseDateMovie = getActivity().findViewById(R.id.tvReleaseDate);

        Movie movie = Parcels.unwrap(getArguments().getParcelable("MOVIES"));

        titleMovie.setText(movie.getTitle());
        descriptionMovie.setText(movie.getOverview());
        ratingMovie.setText(String.valueOf(movie.getVoteAverage()));
        realiseDateMovie.setText(movie.getReleaseDate());

        Picasso.get()
                .load(movie.getPosterPath())
                .into(posterMovie);
    }
}
