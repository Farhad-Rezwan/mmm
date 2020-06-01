package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.entity.MovieEntity;
import com.example.myapplication.fragment.MovieViewFragment;
import com.example.myapplication.memoirpersoncinemacred.MovieSearch;

import java.io.Serializable;
import java.util.ArrayList;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.WatchlistItemViewHolder> {

    private Context eContext;
    private ArrayList<MovieEntity> wList;
    private String movieName, releaseDate, datetime;

    public WatchListAdapter(Context context, ArrayList<MovieEntity> movieList) {
        eContext = context;
        wList = movieList;
    }


    @NonNull
    @Override
    public WatchlistItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(eContext).inflate(R.layout.watchlist_list, parent, false);
        return new WatchListAdapter.WatchlistItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistItemViewHolder holder, int position) {
        MovieEntity currMovieSearch = wList.get(position);

        movieName = currMovieSearch.getFirstName();
        releaseDate = currMovieSearch.getLastName();
        datetime = currMovieSearch.getDate();

        holder.movieNameTV.setText(movieName);
        holder.releaseYearTV.setText(releaseDate);
        holder.dateTimeTv.setText(datetime);

    }

    @Override
    public int getItemCount() {
        return wList.size();
    }

    public class WatchlistItemViewHolder extends RecyclerView.ViewHolder {

        public TextView movieNameTV, releaseYearTV, dateTimeTv;
        CardView rootLayout;

        public WatchlistItemViewHolder( View itemView) {
            super(itemView);

            movieNameTV = itemView.findViewById(R.id.movie_name_recycle);
            releaseYearTV = itemView.findViewById(R.id.release_date_recycle);
            dateTimeTv = itemView.findViewById(R.id.movie_date_time);
            rootLayout = itemView.findViewById(R.id.root_cd_view);
        }
    }

}
