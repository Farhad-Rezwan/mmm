package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.save.PersonObject;


public class HomeFragment extends Fragment {
    private TextView textView;
    private PersonObject personObject;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the View for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        personObject = (PersonObject) getActivity().getApplicationContext();

        textView = view.findViewById(R.id.tv);
        textView.setText(personObject.getPerson().getFirstname());
        return view;
    }
}
