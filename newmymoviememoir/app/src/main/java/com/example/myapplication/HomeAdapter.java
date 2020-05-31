package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.fragment.MovieViewFragment;
import com.example.myapplication.memoirpersoncinemacred.MovieSearch;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeItemViewHolder> {


    private Context eContext;
    private ArrayList<MovieSearch> highestMovieList;
    private String movieName, releaseDate, ratingScore;

    public HomeAdapter(Context context, ArrayList<MovieSearch> movieList) {
        eContext = context;
        highestMovieList = movieList;
    }

    @Override
    public HomeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(eContext).inflate(R.layout.home_item, parent, false);
        return new HomeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeItemViewHolder holder, int position) {
        MovieSearch currMovieSearch = highestMovieList.get(position);

        movieName = currMovieSearch.getMovieName();
        releaseDate = currMovieSearch.getReleaseYear();
        ratingScore = currMovieSearch.getRate();

        holder.hMovieNameTV.setText(movieName);
        holder.hRatingTV.setText(ratingScore);
        holder.hReleaseYearTV.setText(releaseDate);

        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInNewFragment();

            }
        });

    }



    private void showInNewFragment() {
        FragmentManager fragmentManager = ((HomeActivity)eContext).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MovieViewFragment movieViewf = new MovieViewFragment();
        MovieSearch mSearch = new MovieSearch(movieName, releaseDate, ratingScore);


        Bundle msBundle = new Bundle();
        msBundle.putSerializable("movieHighestResult", (Serializable) mSearch);
        movieViewf.setArguments(msBundle);
        fragmentTransaction.replace(R.id.content_frame, movieViewf);
        fragmentTransaction.commit();


    }

    @Override
    public int getItemCount() {
        return highestMovieList.size();
    }

    public class HomeItemViewHolder extends RecyclerView.ViewHolder {

        public TextView hMovieNameTV, hReleaseYearTV, hRatingTV;
        CardView rootLayout;

        public HomeItemViewHolder( View itemView) {
            super(itemView);

            hMovieNameTV = itemView.findViewById(R.id.home_item_movie_name_recycle);
            hReleaseYearTV = itemView.findViewById(R.id.home_item_release_date_recycle);
            hRatingTV = itemView.findViewById(R.id.home_item_movie_rating_recycle);
            rootLayout = itemView.findViewById(R.id.home_item_root_card_view);
        }
    }
}
