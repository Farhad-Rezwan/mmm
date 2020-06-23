package com.example.myapplication.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.memoirpersoncinemacred.Cinema;
import com.example.myapplication.memoirpersoncinemacred.MovieSearch;
import com.example.myapplication.networkconnection.NetworkConnection;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.ParseException;

public class CinemaAddFragment extends Fragment {


    private static final String TAG = "CinemaAddFragment";

    NetworkConnection networkConnection = null;

    private Cinema cinema;
    private MovieSearch movieSearch;

    private EditText cinemaName, cinemaSuburb;
    private Button cinemaAddBtn;
    private String cName, cSuburb;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();

        // using the bundle to transfer movieSearch object.
        if (null != bundle) movieSearch = (MovieSearch) bundle.getSerializable("bundleForMemoir");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cinema_add_fragment, container, false);

        cinemaName = view.findViewById(R.id.cinema_add_name);
        cinemaSuburb = view.findViewById(R.id.cinema_add_suburb);


        networkConnection = new NetworkConnection();


        cinemaAddBtn = view.findViewById(R.id.cinema_add_button);
        cinemaAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // this method validates whether cinema is added or not.
                addCinema();
            }
        });

        return view;
    }

    // innitialising the cinema.
    public void addCinema() {
        initialize();
        if (!validate()) {
            Toast.makeText(getActivity(), "Cinema has failed", Toast.LENGTH_SHORT).show();
        }
        else {

            onCinemaAddSuccess();
        }
    }
    public void initialize() {
        cName = cinemaName.getText().toString().trim();
        cSuburb = cinemaSuburb.getText().toString().trim();
    }

    public boolean validate() {
        boolean valid = true;
        if (cName.isEmpty() || cName.length() > 32){
            cinemaName.setError("Please enter valid cinema name");
            valid = false;
        }
        if (cSuburb.isEmpty() || cSuburb.length() > 32){
            cinemaSuburb.setError("Please enter valid sur name");
            valid = false;
        }

        return valid;

    }

    // starts the async task for adding the cinema
    public void onCinemaAddSuccess() {

        String[] details = {cName, cSuburb};
        if (details.length == 2) {
            AddCinemaTask AddCinemaTask = new AddCinemaTask();
            AddCinemaTask.execute(details);
        }


    }

    private class AddCinemaTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result= "The cinema with name: " + params[0] + " was added";
            Log.d(TAG, "doInBackground: "  + result);
            return networkConnection.addCinema(params);

        }
        @Override
        protected void onPostExecute (String result) {
            cinema = null;

            if (!(result == null)){
                JsonElement jelement = new JsonParser().parse(result);
                JsonObject jObject = jelement.getAsJsonObject();
                String cinemaNameStr = jObject.get("cinemaname").getAsString();
                String  cinemaSuburbStr = jObject.get("cinemasuburb").getAsString();



                cinema = new Cinema(cinemaNameStr, cinemaSuburbStr);

            } else {
                Toast.makeText(getActivity(), " Error Occurred", Toast.LENGTH_SHORT).show();
            }

            if (cinema == null){
                Toast.makeText(getActivity(), "cinema add has failed", Toast.LENGTH_SHORT).show();
            } else  {
                onCinemaRegestrationSuccess();
            }


        }
    }


    // when the cinema regestration succeed, this method starts the Fragment Transaction
    public void onCinemaRegestrationSuccess() {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MemoirAddFragment memoirAddFragment = new MemoirAddFragment();

        Bundle bundleForMemoir = new Bundle();
        bundleForMemoir.putSerializable("CinemaAdd", cinema);
        bundleForMemoir.putSerializable("bundleForMemoir", movieSearch);
        memoirAddFragment.setArguments(bundleForMemoir);
        fragmentTransaction.replace(R.id.content_frame, memoirAddFragment);
        fragmentTransaction.commit();


    }





}





