package com.example.android.az.popularmoviesapp;

public class Movie {
    private String mOriginalTitle;
    private String mReleaseDate;
    private String mPosterImageThumbnail;
    private String mAPlotSynopsis;
    private String mUserRating;

    public Movie(String mOriginalTitle, String mReleaseDate, String mPosterImageThumbnail, String mAPlotSynopsis, String mUserRating) {
        this.mOriginalTitle = mOriginalTitle;
        this.mReleaseDate = mReleaseDate;
        this.mPosterImageThumbnail = mPosterImageThumbnail;
        this.mAPlotSynopsis = mAPlotSynopsis;
        this.mUserRating = mUserRating;
    }

    public String getmOriginalTitle() {
        return mOriginalTitle;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmPosterImageThumbnail() {
        return mPosterImageThumbnail;
    }

    public String getmAPlotSynopsis() {
        return mAPlotSynopsis;
    }

    public String getmUserRating() {
        return mUserRating;
    }
}
