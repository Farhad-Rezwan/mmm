package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.memoirpersoncinemacred.MovieSearch;
import com.example.myapplication.networkconnection.NetworkConnection;

public class WatchlistFragment extends Fragment {


    private RecyclerView watchlistRecyclerView;



    NetworkConnection networkConnection = null;

    private MovieSearch movieSearch;

    public WatchlistFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (null != bundle) movieSearch = (MovieSearch) bundle.getSerializable("movieHighestResult");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.watchlist_fragment, container, false);


        networkConnection = new NetworkConnection();

        watchlistRecyclerView = (RecyclerView) view.findViewById(R.id.wl_home_recycler_v);
        watchlistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        return view;
    }

}
