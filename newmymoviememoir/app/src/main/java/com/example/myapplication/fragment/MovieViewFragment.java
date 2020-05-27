package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.memoirpersoncinemacred.MovieSearch;
import com.example.myapplication.R;

public class MovieViewFragment extends Fragment {

//    private RecyclerView mRecyclerView;
//    private ExampleAdapter mExampleAdapter;
//    private ArrayList<MovieSearch> mMovieSearchList;
//    private RequestQueue mRequestQueue;


    private MovieSearch movieSearch;



    private TextView textView;
    public MovieViewFragment(){

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle !=null) {
            movieSearch = (MovieSearch) bundle.getSerializable("MovieSearch");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movieview_fragment, container, false);













//        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        mMovieSearchList = new ArrayList<>();
//
//        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());



//        textView = view.findViewById(R.id.tv_showmessage);
//
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Message", Context.MODE_PRIVATE);
//        String message = sharedPreferences.getString("message", null);
//        textView.setText(message);
        return view;

    }
}
