package com.example.movies.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
@Entity(tableName = "favoritetable", indices = @Index(value = {"idMovie"}, unique = true))
public class Movie {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    private int index;

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
    boolean isFavorite;

    @ColumnInfo(name = "type")
    int type;

    @Ignore
    public Movie() {
    }

    @Ignore
    public Movie(int voteCount, int id, boolean video, double voteAverage, String title,
                 double popularity, String posterPath, String originalLanguage, String originalTitle,
                 List<Integer> genreIds, String backdropPath, boolean adult, String overview,
                 String releaseDate, int type) {
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
        this.type = type;
    }

    public Movie(int index, int voteCount, int idMovie, boolean video, double voteAverage, String title,
                 double popularity, String posterPath, String originalLanguage, String originalTitle,
                 String backdropPath, boolean adult, String overview,
                 String releaseDate, boolean isFavorite, int type) {
        this.index = index;
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
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int id) {
        this.index = id;
    }

    public void isFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    private String imagesUrl = "http://image.tmdb.org/t/p/w185";

    public String getPosterPath() {
        return posterPath;
    }

    public String getFullImageUrl() {
        if(posterPath == null)
            return "https://www.google.com/imgres?imgurl=http%3A%2F%2Fwhite.kz%2Fassets%2Fimg%2F" +
                    "logo_records.svg&imgrefurl=http%3A%2F%2Fwhite.kz%2F&docid=koZoCayEVyAAKM&tbnid" +
                    "=gmGZqaPHA6O6rM%3A&vet=10ahUKEwjj2u2txYLkAhVD1qYKHXeJAucQMwhQKAMwAw..i&w=457&h" +
                    "=800&bih=686&biw=1440&q=white&ved=0ahUKEwjj2u2txYLkAhVD1qYKHXeJAucQMwhQKAMwAw&" +
                    "iact=mrc&uact=8";
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

    public boolean isVideo() {
        return video;
    }

    public boolean isAdult() {
        return adult;
    }

    //1 - топовый, 0 - популярный
    public int getType() {
        return type;
    }

    //1 - топовый, 0 - популярный
    public void setType(int type) {
        this.type = type;
    }
}
