package com.example.myapplication.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MovieViewAdapter;
import com.example.myapplication.R;
import com.example.myapplication.memoirpersoncinemacred.Memoir;
import com.example.myapplication.memoirpersoncinemacred.MovieSearch;
import com.example.myapplication.networkconnection.NetworkConnection;
import com.example.myapplication.networkconnection.SearchGoogleApi;
import com.example.myapplication.save.MemoirViewAdapter;
import com.example.myapplication.save.PersonObject;

import java.util.ArrayList;

public class MemoirFragment extends Fragment {

    private PersonObject personObject;

    ArrayList<Memoir> memoirArrayList;


    private NetworkConnection networkConnection = null;
    RecyclerView recyclerView;

    public MemoirFragment(){

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.memoir_fragment, container, false);

        networkConnection = new NetworkConnection();
        personObject = (PersonObject) getActivity().getApplicationContext();

        recyclerView = (RecyclerView) view.findViewById(R.id.memoir_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        String personid = personObject.getPerson().getPersonid().toString();
        AsyncMemoirSearch asyncMemoirSearch = new AsyncMemoirSearch();
        asyncMemoirSearch.execute(personid);



        return view;
    }



    private class AsyncMemoirSearch extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return networkConnection.showMemoir(strings);

        }


        @Override
        protected void onPostExecute(String result) {
            memoirArrayList = (ArrayList<Memoir>) networkConnection.getObjects(result);

            recyclerView.setAdapter(new MemoirViewAdapter(getActivity(), memoirArrayList));

        }

    }

}
