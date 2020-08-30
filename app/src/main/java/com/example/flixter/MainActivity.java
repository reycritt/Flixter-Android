package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.adapters.MovieAdapter;
import com.example.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    //The API key that links to the JSON
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";//Tag to log data (see "onSuccess")

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();
        RecyclerView rvMovies = findViewById(R.id.rvMovies);

        //Create adapter
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);//this, data
        //Set adapter on recyclerView
        rvMovies.setAdapter(movieAdapter);
        //Set layoutmanager on recyclerview (to know how to layout views on screen)
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        //MovieDB uses JSON, so we use JsonHTTPHandler
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.e(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;//Returns json object from JSON
                //Make sure to check JSON to find correct object; the array contains all objects/movies
                try {
                    JSONArray results = jsonObject.getJSONArray("results");

                    //Log.i() is a way to display info in logcat (besides sout)
                    Log.i(TAG, "Results: " + results.toString());

                    /*
                    addAll is here to avoid creating new adapters
                    Make note: set height of Recycler to match content, not parent
                     */
                    movies.addAll(Movie.fromJsonArray(results));//JSON array used in Movie constructor to create movies
                    movieAdapter.notifyDataSetChanged();//Refreshes to load changes
                    Log.i(TAG, "Movies: " + movies.size());//Breakpooint one line above to check size

                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                    e.printStackTrace();
                }

                /*Create breakpoints by clicking into the margin next to a line/statement
                (creates a red dot).
                Run Debug (the bug shapre 3 icons to the right of "Run"), and the app
                will run and stop once/if it reaches the break
                 */
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onFailure");
            }
        });
    }
}