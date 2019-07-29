package com.example.movies.ui.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.movies.R;
import com.example.movies.presenters.MoviePresenter;

public class SettingsSortingFragment extends MvpAppCompatFragment{

    static MoviePresenter pres;

    public static SettingsSortingFragment create(MoviePresenter presenter) {
        SettingsSortingFragment fragment = new SettingsSortingFragment();
        pres = presenter;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sorting, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        RadioGroup radioGroup = getActivity().findViewById(R.id.rgSorting);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdPopularMovies:
                        pres.loadPopularMovies();
                        break;
                    case R.id.rdHeightRatedMovies:
                        pres.loadHeightRatedMovies();
                        break;
                    default:
                        break;
                }
            }
        });
    }

}
