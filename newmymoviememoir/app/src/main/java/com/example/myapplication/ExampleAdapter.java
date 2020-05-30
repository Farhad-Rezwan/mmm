package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExViewHolder> {


    private Context eContext;
    private ArrayList<MovieSearch> eMovieList;
    private String imageURL, movieName, movieYear, information;
    private String rate;

    public ExampleAdapter(Context context, ArrayList<MovieSearch> movieList) {
        eContext = context;
        eMovieList = movieList;
    }

    @Override
    public ExViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(eContext).inflate(R.layout.example_item, parent, false);
        return new ExViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExViewHolder holder, int position) {
        MovieSearch currMovieSearch = eMovieList.get(position);

        imageURL = currMovieSearch.getMovieURL();
        movieName = currMovieSearch.getMovieName();
        movieYear = currMovieSearch.getReleaseYear();
        information = currMovieSearch.getInfor();
        rate = currMovieSearch.getRate();

        holder.eTextViewName.setText(movieName);
        holder.eTextViewYear.setText(movieYear);
        Picasso.get().load(imageURL).into(holder.eImageView); //.resize

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
        MovieSearch mSearch = new MovieSearch(movieName, movieYear, imageURL, information, rate);


        Bundle msBundle = new Bundle();
        msBundle.putSerializable("movieSearchResult", (Serializable) mSearch);
        movieViewf.setArguments(msBundle);
        fragmentTransaction.replace(R.id.content_frame, movieViewf);
        fragmentTransaction.commit();


    }

    @Override
    public int getItemCount() {
        return eMovieList.size();
    }

    public class ExViewHolder extends RecyclerView.ViewHolder {

        public ImageView eImageView;
        public TextView eTextViewName;
        public TextView eTextViewYear;
        CardView rootLayout;

        public ExViewHolder( View itemView) {
            super(itemView);

            eImageView = itemView.findViewById(R.id.mcard_image);
            eTextViewName = itemView.findViewById(R.id.mtitle_view);
            eTextViewYear = itemView.findViewById(R.id.myear_view);
            rootLayout = itemView.findViewById(R.id.boxid);
        }
    }
}
