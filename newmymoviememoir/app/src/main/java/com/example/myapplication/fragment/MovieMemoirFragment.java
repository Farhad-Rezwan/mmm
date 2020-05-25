package com.example.myapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import org.w3c.dom.Text;

public class MovieMemoirFragment extends Fragment {
    private TextView textView;
    public MovieMemoirFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.moviememoir_fragment, container, false);

        textView = view.findViewById(R.id.tv_showmessage);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Message", Context.MODE_PRIVATE);
        String message = sharedPreferences.getString("message", null);
        textView.setText(message);
        return view;

    }
}
