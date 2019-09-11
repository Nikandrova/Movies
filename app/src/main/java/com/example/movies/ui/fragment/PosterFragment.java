package com.example.movies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.movies.R;
import com.squareup.picasso.Picasso;

public class PosterFragment extends Fragment {
    ImageView imageView;
    static String url;

    public final static PosterFragment getInstance(String urlImage){
        url = urlImage;
        return new PosterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_poster,
                                            container, false);

        imageView = (ImageView) viewGroup.findViewById(R.id.ivMoviePoster);
        Picasso.get()
                .load(url)
                .into(imageView);

        return  viewGroup;
    }
}
