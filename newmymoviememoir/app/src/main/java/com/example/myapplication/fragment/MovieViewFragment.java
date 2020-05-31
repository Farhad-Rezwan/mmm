package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.memoirpersoncinemacred.MovieSearch;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

public class MovieViewFragment extends Fragment {

    private MovieSearch movieSearch;

    private TextView name, releaseYear, infor;
    private ImageView movieImageIVView;
    private RatingBar rate;
    private Button buttonToMemoir;

    public MovieViewFragment(){

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //getting bundle value
        Bundle bundle = this.getArguments();
        if (null != bundle) movieSearch = (MovieSearch) bundle.getSerializable("movieSearchResult");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movieview_fragment, container, false);

        name = view.findViewById(R.id.vtitle_view_view);
        rate = view.findViewById(R.id.v_rate_view);
        releaseYear = view.findViewById(R.id.vyear_view_view);
        infor = view.findViewById(R.id.v_infor_view);
        movieImageIVView = view.findViewById(R.id.vcard_imageView);

        name.setText(movieSearch.getMovieName());



        rate.setEnabled(false);
        rate.setMax(5);
        rate.setStepSize(0.01f);
        rate.setRating(Float.parseFloat(movieSearch.getRate()));
        rate.invalidate();



        releaseYear.setText(movieSearch.getReleaseYear());
        infor.setText(movieSearch.getInfor());
        Picasso.get().load(movieSearch.getMovieURL()).into(movieImageIVView);


        buttonToMemoir = view.findViewById(R.id.m_add_to_memoir_button);
        buttonToMemoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MemoirAddFragment memoirAddFragment = new MemoirAddFragment();

                Bundle bundleForMemoir = new Bundle();
                bundleForMemoir.putSerializable("bundleForMemoir", movieSearch);
                memoirAddFragment.setArguments(bundleForMemoir);
                fragmentTransaction.replace(R.id.content_frame, memoirAddFragment);
                fragmentTransaction.commit();
            }
        });




        return view;

    }
}
