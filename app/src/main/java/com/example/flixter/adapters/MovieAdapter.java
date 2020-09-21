package com.example.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.DetailActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;//Where adapter is constructed from
    List<Movie> movies;//The list/data to be displayed (adapter holds)

    //Generate constructor to handle context and data (right click!)
    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    /*
    Inflates layout and return in viewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        //Create view in inflate item xml, in this case item_movie
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    /*
    Populates data into view through viewHolder
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {//Position is somewhere on screen
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        //Get movie position
        Movie movie = movies.get(position);//Creates a movie (overview, poster, etc) from position
        //Bind movie data in viewHolder
        holder.bind(movie);//Bind, or place, Movie movie to holder (requires new method)
    }

    /*
    Returns total count of items (generally list/data size)
     */
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //Start off with this; represents the row in xml movie_item
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout container;

        /*
        Create all views from xml styled format
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            //Sets the title color to orange if the rating is >= 7
            if (movie.getRating() >= 7.0) {
                tvTitle.setTextColor(Color.parseColor("#FF8C00"));
            }

            Log.d("MovieAdapter", "Overview Height: " + tvOverview.getMeasuredHeightAndState());

            String imageURL;//Change image based on orientation (posterPath or backdropPath)
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageURL = movie.getBackdropPath();
            } else {
                imageURL = movie.getPosterPath();
            }
            //Create Glide dependency to display images
            Glide.with(context).load(imageURL).into(ivPoster);

            //Register clickListener on whole container; renamed the item xml's "container"
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Navigate to new activity with Intent (w/ context and class)
                    Intent i = new Intent(context, DetailActivity.class);
                    //i.putExtra("title", movie.getTitle());//Sends data from this class to new Activity/Intent
                    //Requires Parse data, used dependency for Parcelable to wrap movie, requires changes in model(s)
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });

        }
    }
}
