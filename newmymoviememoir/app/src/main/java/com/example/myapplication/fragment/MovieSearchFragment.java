package com.example.myapplication.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MovieViewAdapter;
import com.example.myapplication.memoirpersoncinemacred.MovieSearch;
import com.example.myapplication.R;
import com.example.myapplication.networkconnection.SearchGoogleApi;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieSearchFragment extends Fragment implements Serializable {

    private ArrayList<MovieSearch> mMovieSearchList;
    private String keyword;
    RecyclerView recyclerView;

    public MovieSearchFragment() {
        mMovieSearchList = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.moviesearch_fragment, container, false);

        final EditText editText = view.findViewById(R.id.movie_search_edit);

        recyclerView = (RecyclerView) view.findViewById(R.id.movie_search_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));




        Button btnSearch = view.findViewById(R.id.movie_search_button);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = editText.getText().toString();

                AsyncMovieSearch asyncMovieSearch = new AsyncMovieSearch();
                asyncMovieSearch.execute();


            }
        });

        return view;
    }

    private class AsyncMovieSearch extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return SearchGoogleApi.search(keyword, new String[]{"num"}, new String[]{"10"});

        }
        @Override
        protected void onPostExecute(String result) {
            mMovieSearchList = (ArrayList<MovieSearch>) SearchGoogleApi.getObjects(result);

            recyclerView.setAdapter(new MovieViewAdapter(getActivity(), mMovieSearchList));

        }

    }
}
