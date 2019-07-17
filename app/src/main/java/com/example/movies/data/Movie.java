package com.example.movies.data;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Movie {

    int vote_count;
    int id;
    boolean video;
    double vote_average;
    String title;
    double popularity;
    String poster_path;
    String original_language;
    String original_title;
    List<Integer> genre_ids;
    String backdrop_path;
    boolean adult;
    String overview;
    String release_date;

    public Movie() {
    }

    public Movie(int voteCount, int id, boolean video, double voteAverage, String title,
                 double popularity, String posterPath, String originalLanguage, String originalTitle,
                 List<Integer> genreIds, String backdropPath, boolean adult, String overview,
                 String releaseDate) {
        this.vote_count = voteCount;
        this.id = id;
        this.video = video;
        this.vote_average = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.poster_path = posterPath;
        this.original_language = originalLanguage;
        this.original_title = originalTitle;
        this.genre_ids = genreIds;
        this.backdrop_path = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.release_date = releaseDate;
    }

    private String imagesUrl = "http://image.tmdb.org/t/p/w185";


    public String getPosterPath() {
        return "http://image.tmdb.org/t/p/w185" + poster_path;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getVoteAverage() {
        return vote_average;
    }

    public int getId() {
        return id;
    }

    public int getVoteCount() {
        return vote_count;
    }

    public List<Integer> getGenreIds() {
        return genre_ids;
    }

    public String getBackdropPath() {
        return backdrop_path;
    }

    public String getOriginalLanguage() {
        return original_language;
    }

    public String getOriginalTitle() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public String getReleaseDate() {
        return release_date;
    }
}
