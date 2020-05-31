package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class CinemaAddFragment extends Fragment {

    private EditText cinemaName, cinemaSuburb;
    private Button cinemaAddBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cinema_add_fragment, container, false);

        cinemaName = view.findViewById(R.id.cinema_add_name);
        cinemaSuburb = view.findViewById(R.id.cinema_add_suburb);




        cinemaAddBtn = view.findViewById(R.id.cinema_add_button);
        cinemaAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addCinema();
            }
        });

        return view;
    }

    public void addCinema() {

    }
}
