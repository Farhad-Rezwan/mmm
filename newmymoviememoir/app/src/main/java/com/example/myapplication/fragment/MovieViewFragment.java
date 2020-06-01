package com.example.myapplication.fragment;

import android.os.AsyncTask;
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

import com.example.myapplication.database.WatchListDatabase;
import com.example.myapplication.entity.MovieEntity;
import com.example.myapplication.memoirpersoncinemacred.MovieSearch;
import com.example.myapplication.R;
import com.example.myapplication.viewmodel.CustomerViewModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieViewFragment extends Fragment {

    WatchListDatabase db = null;

    private MovieSearch movieSearch;

    private WatchListDatabase watchListDatabase = null;

    private TextView name, releaseYear, infor, roomTest;
    private ImageView movieImageIVView;
    private RatingBar rate;
    private Button buttonToMemoir, addToWatchlist;
    CustomerViewModel customerViewModel;


    private ArrayList<MovieEntity> movieEntityArrayList;

    public MovieViewFragment(){

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //getting bundle value
        Bundle bundle = this.getArguments();
        if (null != bundle) movieSearch = (MovieSearch) bundle.getSerializable("movieSearchResult");
        watchListDatabase = (WatchListDatabase) WatchListDatabase.getInstance(getActivity());
        movieEntityArrayList = new ArrayList<>();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movieview_fragment, container, false);

        name = view.findViewById(R.id.vtitle_view_view);
        rate = view.findViewById(R.id.v_rate_view);
        releaseYear = view.findViewById(R.id.vyear_view_view);
        infor = view.findViewById(R.id.v_infor_view);
        movieImageIVView = view.findViewById(R.id.vcard_imageView);
        roomTest = view.findViewById(R.id.room_resting);

        name.setText(movieSearch.getMovieName());

        db = WatchListDatabase.getInstance(getActivity());


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



//        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
//        customerViewModel.initalizeVars(getActivity().getApplication());
//        customerViewModel.getAllCustomers().observe(this, new Observer<List<Customer>>() {
//            @Override
//            public void onChanged(List<Customer> customers) {
//                String allCustomers = "";
//                for (Customer temp : customers) {
//                    String customerstr = (temp.getUid() + " " + temp.getFirstName() + " " + temp.getLastName() + " " + temp.getDate());
//                    allCustomers = allCustomers + System.getProperty("line.separator") + customerstr;
//                }
//                roomTest.setText("All data: " + allCustomers);
//
//            }
//
//        });








        addToWatchlist = view.findViewById(R.id.m_add_to_watchlist_button);
        addToWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(name.getText().toString().isEmpty())) {
                    if (!(releaseYear.getText().toString().isEmpty())) {

                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date dateTimeWatched = new Date();

                        String[] details = {name.getText().toString(), releaseYear.getText().toString(), formatter.format(dateTimeWatched)};
                        if (details.length == 3) {
                            InsertDatabase addDatabase = new InsertDatabase();
                            addDatabase.execute(details);
                        }

                    }

                    //splitting three parts of the text based on the space between them


                }

            }
        });


        return view;

    }


    private class InsertDatabase extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            MovieEntity movieEntity = new MovieEntity(params[0], params[1], params[2]);
            long id = db.customerDao().insert(movieEntity);
            return (id + " " + params[0] + " " +  params[1] + " " + params[2]);
        }
        @Override
        protected void onPostExecute (String details) {
            roomTest.setText("Added Record" + details);
        }

    }


}
