package com.example.myapplication.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.HomeAdapter;
import com.example.myapplication.R;
import com.example.myapplication.memoirpersoncinemacred.Cinema;
import com.example.myapplication.memoirpersoncinemacred.MovieSearch;
import com.example.myapplication.networkconnection.NetworkConnection;
import com.example.myapplication.save.PersonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;


public class HomeFragment extends Fragment {

    private TextView userFirstName, currentDate, homeTopMovieYear;
    private RecyclerView homeRecyclerView;
    private PersonObject personObject;


    NetworkConnection networkConnection = null;

    private MovieSearch movieSearch;

    public HomeFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //getting bundle value
        Bundle bundle = this.getArguments();
        if (null != bundle) movieSearch = (MovieSearch) bundle.getSerializable("movieHighestResult");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);



        networkConnection = new NetworkConnection();

        personObject = (PersonObject) getActivity().getApplicationContext();

        userFirstName = view.findViewById(R.id.home_user_name);
        currentDate = view.findViewById(R.id.home_curr_date);
        homeTopMovieYear = view.findViewById(R.id.home_top_movie);

        homeRecyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_v);


    //  setting person first name for
        userFirstName.setText(personObject.getPerson().getFirstname());

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        currentDate.setText(formattedDate);

        String thisYear = new SimpleDateFormat("yyyy").format(new Date());
        homeTopMovieYear.setText("Top movie for " + thisYear);


        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        getting the person id from the person object using appilication context

        String [] details = {personObject.getPerson().getPersonid().toString()};
//        String [] details = {"3"}; // testing purpose

        AsyncHighestMovie asyncHighestMovie = new AsyncHighestMovie();
        asyncHighestMovie.execute(details);




        return view;
    }

    private class AsyncHighestMovie extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return networkConnection.getRecentHighestRatedMovie(strings);

        }
        @Override
        protected void onPostExecute(String result) {
            ArrayList<MovieSearch> mMovieHighestList = new ArrayList<>();


            JsonArray jsonArray = (JsonArray) new JsonParser().parse(result);

            JsonObject jsonObject;

            int arraySize = jsonArray.size();
            for (int i = 0; i < arraySize; i++) {
                jsonObject = jsonArray.get(i).getAsJsonObject();
                String movieName = jsonObject.get("movieName").getAsString();
                String ratingScore = jsonObject.get("ratingScore").getAsString();
                String releaseDate = jsonObject.get("releaseDate").getAsString();
                mMovieHighestList.add(new MovieSearch(movieName, releaseDate, ratingScore));
            }

            homeRecyclerView.setAdapter(new HomeAdapter(getActivity(), mMovieHighestList));

        }

    }





}
