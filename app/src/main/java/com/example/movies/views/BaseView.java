package com.example.movies.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface BaseView extends MvpView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showProgress();
    @StateStrategyType(OneExecutionStateStrategy.class)
    void hideProgress();
}
