package com.example.myapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.MovieSearch;
import com.example.myapplication.R;
import com.example.myapplication.networkconnection.SearchGoogleApi;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchFragment extends Fragment {

    public MovieSearchFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.moviesearch_fragment, container, false);




        final TextView tv= view.findViewById(R.id.tv_Result);
        final EditText editText=view.findViewById(R.id.ed_keyword);
        Button btnSearch = view.findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String keyword = editText.getText().toString(); //create an anonymous AsyncTask
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        return SearchGoogleApi.search(keyword, new String[]{"num"},
                                new String[]{"3"});
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        List<MovieSearch> result2 = new ArrayList<MovieSearch>();
                        result2 = SearchGoogleApi.getObjects(result);

                        tv.setText(SearchGoogleApi.getSnippet(result + result2));
                    }
                }.execute();
            }

        });
        return view;
    }
}
