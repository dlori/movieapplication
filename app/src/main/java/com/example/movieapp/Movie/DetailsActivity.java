package com.example.movieapp.Movie;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.movieapp.Database.DatabaseHelperFavorite;
import com.example.movieapp.R;

public class DetailsActivity extends AppCompatActivity {


    TextView nameOfMovie, plotSynopsis, userRating, releaseDate;
    ImageView imageView;
    MovieDetails movie;
    String thumbnail, movieName, synopsis, rating, dateOfRelease;
    int movie_id;
    private Button exit, favorite;
    private DatabaseHelperFavorite databaseHelperFavorite;
    private AppCompatActivity appCompatActivity = DetailsActivity.this;
    private MovieDetails movieDetails;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        init();

        setData();

        exitFromDetails();

        addToFavorite();

    }

    private void addToFavorite() {

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              saveFavorite();
            }
        });
    }

    private void saveFavorite() {
        movieDetails = new MovieDetails();

        Double rate = movie.getVoteAverage();


        movieDetails.setId(movie_id);
        movieDetails.setOriginalTitle(movieName);
        movieDetails.setPosterPath(thumbnail);
        movieDetails.setVoteAverage(rate);
        movieDetails.setOverview(synopsis);

        databaseHelperFavorite.addFavoriteList(movieDetails);

        Toast.makeText(getApplicationContext(), "Added to favorite", Toast.LENGTH_SHORT).show();
    }

    private void exitFromDetails() {

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setData() {

        Intent intentThatStartedThisActivity = getIntent();
        if(intentThatStartedThisActivity.hasExtra("movies")){
            movie = getIntent().getParcelableExtra("movies");

            thumbnail = movie.getPosterPath();
            movieName = movie.getOriginalTitle();
            synopsis = movie.getOverview();
            rating = Double.toString(movie.getVoteAverage());
            dateOfRelease = movie.getReleaseDate();
            movie_id = movie.getId();

            String poster = "https://image.tmdb.org/t/p/w500" + thumbnail;

            Glide.with(this)
                    .load(poster)
                    .placeholder(R.drawable.load)
                    .into(imageView);

            nameOfMovie.setText(movieName);
            plotSynopsis.setText(synopsis);
            userRating.setText(rating);
            releaseDate.setText(dateOfRelease);

        }else{
            Toast.makeText(this,"No API data", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {

        imageView = findViewById(R.id.thumbnail_image_header_movie);
        nameOfMovie = findViewById(R.id.titleOfMovie);
        plotSynopsis = findViewById(R.id.descriptionOfMovie);
        userRating = findViewById(R.id.userRatingOfMovie);
        releaseDate = findViewById(R.id.releaseDateOfMovie);
        exit = findViewById(R.id.buttonExit);
        favorite = findViewById(R.id.addToFavorite);
        databaseHelperFavorite = new DatabaseHelperFavorite(appCompatActivity);
    }
}
