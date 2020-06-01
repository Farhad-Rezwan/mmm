package com.example.myapplication.save;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.MovieViewAdapter;
import com.example.myapplication.R;
import com.example.myapplication.fragment.MovieViewFragment;
import com.example.myapplication.memoirpersoncinemacred.Memoir;
import com.example.myapplication.memoirpersoncinemacred.MovieSearch;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class MemoirViewAdapter extends RecyclerView.Adapter<MemoirViewAdapter.MemoirViewItemViewHolder> {




    private Context eContext;
    private ArrayList<Memoir> eMemoirList;
    private String moviename;
    private Date moviereleasedate;
    private Date datetimewatched;
    private String comment;
    private BigDecimal rating;
    private String suburb;
    private String url;


    public MemoirViewAdapter(Context eContext, ArrayList<Memoir> eMemoirList) {
        this.eContext = eContext;
        this.eMemoirList = eMemoirList;
    }

    @Override
    public MemoirViewItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(eContext).inflate(R.layout.memoirview_item, parent, false);
        return new MemoirViewAdapter.MemoirViewItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoirViewItemViewHolder holder, int position) {
        Memoir currMemoir = eMemoirList.get(position);

        moviename = currMemoir.getMoviename();
        moviereleasedate = currMemoir.getMoviereleasedate();
        datetimewatched = currMemoir.getDatetimewatched();
        comment = currMemoir.getComment();
        rating = currMemoir.getRating();

        // suburb different table
        url = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcReFeeGK2NfJ24WvZ5tM2WSd7i7ETwTDcf_z22IemeFLJj7h64gaEjokz5B";
        // image api call
        suburb = "caulfield";

        Picasso.get().load(url).into(holder.memoirImageView); //.resize
        holder.movieNameTv.setText(moviename);
        holder.movieReleaseDateTv.setText("10/10/10");
        holder.movieDateTimeTv.setText("11/11/11");
        holder.commentTv.setText(comment);
        holder.memoirRateRb.setNumStars(10);

        holder.rootMemoirLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showInMemoirViewFragment();
            }
        });


    }

//    private void showInNewFragment() {
//        FragmentManager fragmentManager = ((HomeActivity)eContext).getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//         = new MovieViewFragment();
//        MovieSearch mSearch = new MovieSearch(movieName, movieYear, imageURL, information, rate);
//
//
//        Bundle msBundle = new Bundle();
//        msBundle.putSerializable("movieSearchResult", (Serializable) mSearch);
//        movieViewf.setArguments(msBundle);
//        fragmentTransaction.replace(R.id.content_frame, movieViewf);
//        fragmentTransaction.commit();


//    }

    @Override
    public int getItemCount() {
        return eMemoirList.size();
    }


    public class MemoirViewItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView memoirImageView;
        public TextView movieNameTv, movieReleaseDateTv, movieDateTimeTv, cinemaSuburbTv, commentTv;
        public RatingBar memoirRateRb;
        CardView rootMemoirLayout;

        public MemoirViewItemViewHolder(View itemView) {
            super(itemView);

            memoirImageView = itemView.findViewById(R.id.memoir_view_image);
            movieNameTv = itemView.findViewById(R.id.memoir_view_movie_name);
            movieReleaseDateTv = itemView.findViewById(R.id.memoir_view_movie_release_date);
            movieDateTimeTv = itemView.findViewById(R.id.memoir_view_date_time_watch);
            cinemaSuburbTv = itemView.findViewById(R.id.memoir_view_cinema_suburb);
            commentTv = itemView.findViewById(R.id.memoir_view_comment);
            memoirRateRb = itemView.findViewById(R.id.memoir_view_rating_bar);
            rootMemoirLayout = itemView.findViewById(R.id.root_memoir_card_view);
        }
    }
}
