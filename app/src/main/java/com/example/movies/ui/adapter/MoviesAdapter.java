package com.example.movies.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.movies.R;
import com.example.movies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.PosterViewHolder> {
    private static final String TAG = "MoviesAdapter";
    private List<Movie> movies;

    public MoviesAdapter(List<Movie> movies){
        this.movies = movies;
    }

    //определяем вертску для отдельного элемента списка
    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_poster, viewGroup, false);

        return new PosterViewHolder(view);
    }

    //устанавливаем данные в отедльный элемент списка
    @Override
    public void onBindViewHolder(@NonNull final PosterViewHolder vh, final int index) {
        final Movie currentItem = movies.get(index);
        String imageUrl = currentItem.getFullImageUrl();

        Picasso.get()
                .load(imageUrl)
                .into(vh.ivPoster);

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMovieClick(currentItem);
            }
        });
    }

    public void setMovies(List<Movie> movie) {
        movies = movie;
        notifyDataSetChanged();
    }

    public void onMovieClick(Movie movie){};

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class PosterViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivPoster;

        public PosterViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }
    }
}
