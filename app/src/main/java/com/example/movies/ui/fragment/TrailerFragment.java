package com.example.movies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movies.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;

public class TrailerFragment extends Fragment {

    //YouTubePlayerView youTubePlayerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_trailer,
                                            container, false);
        //youTubePlayerView = viewGroup.findViewById(R.id.pvTrailer);
        return  viewGroup;
    }
}
