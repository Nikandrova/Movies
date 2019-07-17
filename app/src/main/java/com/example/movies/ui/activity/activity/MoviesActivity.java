package com.example.movies.ui.activity.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.movies.R;
import com.example.movies.data.Movie;
import com.example.movies.presenters.MoviePresenter;
import com.example.movies.ui.activity.Adapter.MoviesAdapter;
import com.example.movies.ui.activity.fragment.MovieDetailFragment;
import com.example.movies.views.MovieView;

import org.parceler.Parcels;

import java.util.List;

public class MoviesActivity extends MvpAppCompatActivity implements MovieView {
    private static final String MOVIES_TAG = "MOVIES";

    RecyclerView rvPosters;

    private List<Movie> movies;

    @InjectPresenter
    MoviePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rvPosters = findViewById(R.id.rvPosters);
        rvPosters.setLayoutManager(new GridLayoutManager(this, 2));

        Switch sw = findViewById(R.id.swHeightRatedMovie);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    presenter.loadHeightRatedMovies();
                }else{
                    presenter.loadPopularMovies();
                }
            }
        });

        if (savedInstanceState != null) {
            movies = Parcels.unwrap(savedInstanceState.getParcelable(MOVIES_TAG));
            initAdapter(movies);
        } else {
            presenter.loadPopularMovies();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIES_TAG, Parcels.wrap(movies));
    }

    @Override
    public void onDataLoaded(List<Movie> movies) {
        this.movies = movies;
        initAdapter(movies);
    }

    @Override
    public void onError(Throwable t) {
        String errorMessage = "An error occurred during networking: " + t.getMessage();
        Toast.makeText(MoviesActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void initAdapter(List<Movie> movies) {
        MoviesAdapter adapter = new MoviesAdapter(movies) {
            @Override
            public void onMovieClick(Movie movie) {
                MovieDetailFragment fragment = MovieDetailFragment.create(movie);
                showFragment(fragment, true);
            }
        };
        rvPosters.setAdapter(adapter);
    }

    public void showFragment(Fragment fragment, boolean addToBack, int containerId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.replace(containerId, fragment, fragment.getClass().getName());
        if (addToBack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
    }

    public void showFragment(Fragment fragment, boolean addToBack) {
        showFragment(fragment, addToBack, R.id.container);
    }
}
