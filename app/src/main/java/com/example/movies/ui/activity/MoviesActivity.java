package com.example.movies.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.movies.views.MovieView;

import java.util.List;

public class MoviesActivity extends MvpAppCompatActivity implements MovieView {
    private final static int START_PAGE = 1;
    private int TOTAL_PAGE = 20;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int CURRENT_PAGE = START_PAGE;

    private int typeLoad = 0;

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

        rvPosters.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if (((visibleItemCount + firstVisibleItemPosition) >= totalItemCount)
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= (TOTAL_PAGE - 4)) {
                        if(typeLoad == 0) {
                            CURRENT_PAGE++;
                            presenter.loadPopularMovies(CURRENT_PAGE);
                        } else{
                            CURRENT_PAGE++;
                            presenter.loadHeightRatedMovies(CURRENT_PAGE);
                        }
                    }
                }
            }
        });

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            rvPosters.setLayoutManager(new GridLayoutManager(this, 3));
        }

        bottomNavigationView = findViewById(R.id.btmNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.popularity:
                                CURRENT_PAGE = START_PAGE;
                                typeLoad = 0;
                                presenter.loadPopularMovies(CURRENT_PAGE);
                                menuItem.setChecked(false);
                                Toast.makeText(getApplicationContext(), "POPULARITY",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.top:
                                CURRENT_PAGE = START_PAGE;
                                typeLoad = 1;
                                presenter.loadHeightRatedMovies(CURRENT_PAGE);
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
        presenter.loadPopularMovies(CURRENT_PAGE);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putAll(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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
        if (adapter == null || CURRENT_PAGE == 1) {
            adapter = new MoviesAdapter(movies) {
                @Override
                public void onMovieClick(Movie movie) {
                    Intent intent = new Intent(MoviesActivity.this, MovieDetailActivity.class);
                    intent.putExtra("idMovieDetail", movie.getIdMovie());
                    startActivity(intent);
                }
            };
            rvPosters.setAdapter(adapter);
        } else {
            adapter.setMovies(movies);
        }
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
