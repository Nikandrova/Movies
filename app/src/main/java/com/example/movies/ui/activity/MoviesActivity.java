package com.example.movies.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.movies.R;
import com.example.movies.data.Movie;
import com.example.movies.presenters.MoviePresenter;
import com.example.movies.ui.adapter.MoviesAdapter;
import com.example.movies.ui.fragment.MovieDetailActivity;
import com.example.movies.views.MovieView;

import java.util.List;

public class MoviesActivity extends MvpAppCompatActivity implements MovieView {
    private static final String MOVIES_TAG = "MOVIES";

    private RecyclerView rvPosters;
    private BottomNavigationView bottomNavigationView;
    private MoviesAdapter adapter;

    @InjectPresenter
    MoviePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rvPosters = findViewById(R.id.rvPosters);
        rvPosters.setLayoutManager(new GridLayoutManager(this, 2));
        bottomNavigationView = findViewById(R.id.btmNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.popularity:
                                presenter.loadPopularMovies();
                                menuItem.setChecked(false);
                                Toast.makeText(getApplicationContext(), "POPULARITY",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.top:
                                presenter.loadHeightRatedMovies();
                                menuItem.setChecked(false);
                                Toast.makeText(getApplicationContext(), "TOP",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.favorites:
                                presenter.loadFavoriteMovies();
                                menuItem.setChecked(true);
                                Toast.makeText(getApplicationContext(), "favorites",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });

        presenter.loadPopularMovies();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkedItemSelectedMenu();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onPopularLoaded(List<Movie> movies) {
        initAdapter(movies);
    }

    @Override
    public void onTopLoaded(List<Movie> movies) {
        initAdapter(movies);
    }

    @Override
    public void onMovieLoadedFromDB(List<Movie> movies) {
        initAdapter(movies);
    }

    @Override
    public void onError(Throwable t) {
        String errorMessage = "An error occurred during networking: " + t.getMessage();
        Toast.makeText(MoviesActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void initAdapter(List<Movie> movies) {
        adapter = new MoviesAdapter(movies) {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = new Intent(MoviesActivity.this, MovieDetailActivity.class);
                intent.putExtra("idMovieDetail", movie.getIdMovie());
                startActivity(intent);
            }
        };
        rvPosters.setAdapter(adapter);
    }

//    public void showFragment(Fragment fragment, boolean addToBack, int containerId) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
//        transaction.replace(containerId, fragment, fragment.getClass().getName());
//        if (addToBack) {
//            transaction.addToBackStack(null);
//        }
//        transaction.commitAllowingStateLoss();
//    }
//
//    public void showFragment(Fragment fragment, boolean addToBack) {
//        showFragment(fragment, addToBack, R.id.container);
//    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void checkedItemSelectedMenu() {
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        if(menuItem.isChecked()){
            presenter.loadFavoriteMovies();
            menuItem.setChecked(false);
        }
    }
}
