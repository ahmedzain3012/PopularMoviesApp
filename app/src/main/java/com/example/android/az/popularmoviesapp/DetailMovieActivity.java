package com.example.android.az.popularmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailMovieActivity extends AppCompatActivity {
    TextView mOriginalTitle;
    ImageView mPosterImageThumbnail;
    TextView mAPlotSynopsis;
    TextView mUserRating;
    TextView mReleaseDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        mOriginalTitle = findViewById(R.id.tv_original_title);
        mPosterImageThumbnail = findViewById(R.id.iv_Poster_image_thumbnail);
        mAPlotSynopsis = findViewById(R.id.tv_a_plot_synopsis);
        mUserRating = findViewById(R.id.tv_user_rating);
        mReleaseDate = findViewById(R.id.tv_release_date);
        Intent intent = getIntent();
        /**
         * Intent intent = new Intent(MainActivity.this, DetailMovieActivity.class);
         *                 intent.putExtra("original_title", currentMovie.getmOriginalTitle());
         *                 intent.putExtra("poster_image_thumbnail", currentMovie.getmPosterImageThumbnail());
         *                 intent.putExtra("a_plot_synopsis", currentMovie.getmAPlotSynopsis());
         *                 intent.putExtra("user_rating", currentMovie.getmUserRating());
         *                 intent.putExtra("release_date", currentMovie.getmReleaseDate());
         */
        if (intent.hasExtra("original_title")) {
            mOriginalTitle.setText(intent.getStringExtra("original_title"));
        }
        if (intent.hasExtra("poster_image_thumbnail")) {
            String baseUrl = "http://image.tmdb.org/t/p/w185";
            String posterAddress = intent.getStringExtra("poster_image_thumbnail");
            String url = baseUrl+posterAddress;
            Picasso.with(this).
                    load(url).
                    into(mPosterImageThumbnail);
        }
        if (intent.hasExtra("a_plot_synopsis")) {
            mAPlotSynopsis.setText(intent.getStringExtra("a_plot_synopsis"));
        }
        if (intent.hasExtra("user_rating")) {
            mUserRating.setText(intent.getStringExtra("user_rating"));
        }
        if (intent.hasExtra("release_date")) {
            mReleaseDate.setText(intent.getStringExtra("release_date"));
        }
    }
}
