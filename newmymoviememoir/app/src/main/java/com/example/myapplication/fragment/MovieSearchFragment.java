package com.example.myapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class MovieSearchFragment extends Fragment {
    private EditText editText;
    private Button button;
    public MovieSearchFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.moviesearch_fragment, container, false);
        editText = view.findViewById(R.id.et_message);
        button = view.findViewById(R.id.msearch_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString().trim();
                if (!message.isEmpty()){
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Message", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEditor = sharedPreferences.edit();
                    spEditor.putString("message", message);
                    spEditor.apply();
                }

            }
        });
        return view;
    }
}
