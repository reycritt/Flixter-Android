package com.example.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    /*Classic class; encapsulats what we want our movie to be
    Create new folders (right-click, New, Package, name folder) to store how you
    want things to be designed
     */
    String posterPath;
    String title;
    String overview;
    //The above represents the JSON parts that make a movie

    public Movie (JSONObject jsonObject) throws JSONException {//throws MUST handle
        //Obtains info based on values in the JSON
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
    }

    //Create a movie for every element in JSON Array
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        //Creates new Movie objects; the array stores all the info designed by the Movie class (Projectile class, Bloom class, etc?)
        for (int i = 0; i < movieJsonArray.length(); i++) {
            movies.add( new Movie (movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    /*
    Getters to obtain movie information; right click, Generate, select whatchawant
     */

    /*
    Make note: for images from a JSON, use the bookmarked CodePAth tutorial for a JSON containing
    various widths for images
     */
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);//Where %s is replaced with String after comma
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
