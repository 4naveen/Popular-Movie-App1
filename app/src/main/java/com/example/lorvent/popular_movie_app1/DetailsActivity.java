package com.example.lorvent.popular_movie_app1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lorvent.popular_movie_app1.network.GetMovieDetail;
import com.example.lorvent.popular_movie_app1.utils.AppConfig;


public class DetailsActivity extends AppCompatActivity {
    LinearLayout linearLayout, layout_back;
    int movie_id;
    String trailer_id, call_from;
    TextView movie_name, release_date, run_time, language, genre, overview, rating;
    ImageView play;
    View view;
    Menu action_menu;
    boolean favourite,isFavorite;
    TextView title;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        Log.i("trailerId",AppSession.trailer_id);
     //isMovieFavorite(String.valueOf(movie_id));

        layout_back = (LinearLayout) findViewById(R.id.layout_back);
        call_from = getIntent().getStringExtra("call_from");
        movie_id = getIntent().getIntExtra("movie_id", 0);
        movie_name = (TextView) findViewById(R.id.movie_name);
        release_date = (TextView) findViewById(R.id.release_date);
        run_time = (TextView) findViewById(R.id.runtime);
        language = (TextView) findViewById(R.id.language);
        genre = (TextView) findViewById(R.id.genre);
        overview = (TextView) findViewById(R.id.overview);
        rating = (TextView) findViewById(R.id.rating);
        setSupportActionBar(toolbar);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            // LayoutInflater inflater=LayoutInflater.from(getApplicationContext());
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(R.layout.movie_title, null);
            title = (TextView) v.findViewById(R.id.title);
            title.setSelected(true);
        }

        new GetMovieDetail(rating, movie_name, release_date, run_time, language, genre, overview, DetailsActivity.this, layout_back, actionBar, title)
                .execute(String.valueOf(movie_id), AppConfig.API_KEY);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (call_from.equals("main")) {
            finish();
        } else {
            Intent i = new Intent(DetailsActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
