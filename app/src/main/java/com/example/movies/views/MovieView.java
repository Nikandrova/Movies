package com.example.movies.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.movies.data.Movie;

import java.util.List;

public interface MovieView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onPopularLoaded(List<Movie> movies);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onTopLoaded(List<Movie> movies);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onMovieLoadedFromDB(List<Movie> movies);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onError(Throwable t);
}
