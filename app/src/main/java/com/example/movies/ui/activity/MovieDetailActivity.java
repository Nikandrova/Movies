package com.example.movies.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.movies.R;
import com.example.movies.data.Movie;
import com.example.movies.presenters.MovieDetailPresenter;
import com.example.movies.ui.adapter.PagerImgTrailerAdapter;
import com.example.movies.ui.fragment.PosterFragment;
import com.example.movies.ui.fragment.TrailerFragment;
import com.example.movies.views.MovieDetailView;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends BaseActivity implements MovieDetailView {
    private static final String TAG = "DetailActivity";

    ToggleButton btnFavourites;
    ImageButton shareButton;
    TextView titleMovie, descriptionMovie, ratingMovie, realiseDateMovie;
    ViewPager viewPager;

    PagerImgTrailerAdapter imgTrailerAdapter;

    LinearLayout sliderDotsPanel;
    private int dotsCount;
    private ImageView[] dots;

    @InjectPresenter
    MovieDetailPresenter movieDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
    }

    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.activity_movie);

        btnFavourites = findViewById(R.id.btFavorites);
        shareButton = findViewById(R.id.btShare);
        viewPager = findViewById(R.id.vpPosterTrailer);

        Bundle arguments = getIntent().getExtras();
        int idMovie = Integer.parseInt(arguments.get("idMovieDetail").toString());
        String urlPoster = arguments.get("urlPosterMovieDetail").toString();

        imgTrailerAdapter = new PagerImgTrailerAdapter(getSupportFragmentManager(),
                createFragments(idMovie, urlPoster));
        viewPager.setAdapter(imgTrailerAdapter);

        sliderDotsPanel = findViewById(R.id.llSliderDots);
        dotsCount = imgTrailerAdapter.getCount();

        createDotsForSlider();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++)
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.ic_no_active_dot));

                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.ic_active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        titleMovie = findViewById(R.id.tvTitle);
        descriptionMovie = findViewById(R.id.tvOverview);
        ratingMovie = findViewById(R.id.tvVoteAverage);
        realiseDateMovie = findViewById(R.id.tvReleaseDate);

        movieDetailPresenter.getFavoriteMovie(idMovie);
    }

    @Override
    public void onMovieDetailLoaded(final Movie movie) {
        if (movie.isFavorite()) {
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
                    Snackbar.make(buttonView, "Added to favorite",
                            Snackbar.LENGTH_SHORT).show();
                } else {
                    movieDetailPresenter.deleteFavoriteMovie(movie);

                    Snackbar.make(buttonView, "Removed from favorite",
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        titleMovie.setText(movie.getTitle());
        descriptionMovie.setText(movie.getOverview());
        ratingMovie.setText(String.valueOf(movie.getVoteAverage()));
        realiseDateMovie.setText(movie.getReleaseDate());
    }

    @Override
    public void onTrailerMovie(String movieId) {
    }

    private void createDotsForSlider() {
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.ic_no_active_dot));

            ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            sliderDotsPanel.addView(dots[i], params);
        }
        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_active_dot));
    }

    private List<Fragment> createFragments(int idMovie, String urlPoster) {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(PosterFragment.getInstance(urlPoster));
        movieDetailPresenter.getTrailerMovie(idMovie);
        fragmentList.add(TrailerFragment.getInstance(movieDetailPresenter.getMovie(idMovie).getKeyTrailer()));
        return fragmentList;
    }
}
