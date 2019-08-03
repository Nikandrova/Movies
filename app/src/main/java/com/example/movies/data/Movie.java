package com.example.movies.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
@Entity(tableName = "favoritetable")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    private int id;

    @SerializedName("vote_count")
    @ColumnInfo(name = "voteCount")
    int voteCount;

    @ColumnInfo(name = "idMovie")
    @SerializedName("id")
    int idMovie;

    @ColumnInfo(name = "video")
    boolean video;

    @ColumnInfo(name = "voteAvarage")
    @SerializedName("vote_avarage")
    double voteAverage;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "popularity")
    double popularity;

    @SerializedName("poster_path")
    @ColumnInfo(name = "posterPath")
    String posterPath;

    @SerializedName("original_language")
    @ColumnInfo(name = "originalLanguage")
    String originalLanguage;

    @SerializedName("original_title")
    @ColumnInfo(name = "originalTitle")
    String originalTitle;

    @SerializedName("genre_ids")
    @Ignore
    List<Integer> genreIds;

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdropPath")
    String backdropPath;

    @SerializedName("adult")
    @ColumnInfo(name = "adult")
    boolean adult;

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    String overview;

    @SerializedName("release_date")
    @ColumnInfo(name = "releaseDate")
    String releaseDate;

    @ColumnInfo(name = "isFavorite")
    Boolean isFavorite;

    @Ignore
    public Movie() {
    }

    @Ignore
    public Movie(int voteCount, int id, boolean video, double voteAverage, String title,
                 double popularity, String posterPath, String originalLanguage, String originalTitle,
                 List<Integer> genreIds, String backdropPath, boolean adult, String overview,
                 String releaseDate) {
        this.voteCount = voteCount;
        this.idMovie = id;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genreIds = genreIds;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public Movie(int id, int voteCount, int idMovie, boolean video, double voteAverage, String title,
                 double popularity, String posterPath, String originalLanguage, String originalTitle,
                 String backdropPath, boolean adult, String overview,
                 String releaseDate, Boolean isFavorite) {
        this.id = id;
        this.voteCount = voteCount;
        this.idMovie = idMovie;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.isFavorite = isFavorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    private String imagesUrl = "http://image.tmdb.org/t/p/w185";

    public String getPosterPath() {
        return posterPath;
    }

    public String getFullImageUrl() {
        return imagesUrl.concat(posterPath);
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setImagesUrl(String imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
